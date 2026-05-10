/// <reference types="@sveltejs/kit" />
/// <reference no-default-lib="true"/>
/// <reference lib="esnext" />
/// <reference lib="webworker" />

// Imports:
import { build, files, version } from '$service-worker'

// Initializations:
const worker = self as unknown as ServiceWorkerGlobalScope
const FILES = `cache${version}`
const BASE = '/FormalLanguagesCompilers-QRCode'
const to_cache = build.concat(files)
const staticAssets = new Set(to_cache)
const extraAssets = [
    `${BASE}/runtime/Compiler.jar`,
    `${BASE}/runtime/Runner.jar`,
    `${BASE}/cheerpj/loader.js`,
    `${BASE}/cheerpj/cheerpOS.js`,
    `${BASE}/cheerpj/c.js`,
    `${BASE}/cheerpj/c.html`,
    `${BASE}/cheerpj/cheerpj.css`,
    `${BASE}/cheerpj/cj3.js`,
    `${BASE}/cheerpj/cj3n17.wasm`,
    `${BASE}/cheerpj/cj3.wasm`,
    `${BASE}/cheerpj/17/lib/modules`,
    `${BASE}/cheerpj/17/jre/lib/cheerpj-jsobject.jar`,
    `${BASE}/cheerpj/17/jre/lib/cheerpj-handlers.jar`,
    `${BASE}/cheerpj/17/jre/lib/cheerpj-awt.jar`,
    `${BASE}/cheerpj/17/conf/security/java.security`,
    `${BASE}/cheerpj/etc/users`,
].filter(Boolean)
const allToCache = [...to_cache, ...extraAssets]
const extraStaticAssets = new Set(extraAssets)

// Install Event:
worker.addEventListener('install', (event) => {
  event.waitUntil(
    caches
      .open(FILES)
      .then((cache) => cache.addAll(allToCache))
      .then(() => {
        worker.skipWaiting()
      })
  )
})

// Activation Event:
worker.addEventListener('activate', (event) => {
  event.waitUntil(
    caches.keys().then(async (keys) => {
      for (const key of keys) {
        if (key !== FILES) await caches.delete(key)
      }
      worker.clients.claim()
    })
  )
})

// Fetch & Cache Event:
async function fetchAndCache(request: Request) {
  const cache = await caches.open(`offline${version}`)
  try {
    const response = await fetch(request)
    cache.put(request, response.clone())
    return response
  } catch (err) {
    const response = await cache.match(request)
    if (response) return response
    throw err
  }
}

// Fetch Event:
worker.addEventListener('fetch', (event) => {
  if (event.request.method !== 'GET' || event.request.headers.has('range')) return
  const url = new URL(event.request.url)
  const isHttp = url.protocol.startsWith('http')
  const isDevServerRequest = url.hostname === self.location.hostname && url.port !== self.location.port
  const isStaticAsset = url.host === self.location.host && (staticAssets.has(url.pathname) || extraStaticAssets.has(url.pathname))
  const skipBecauseUncached = event.request.cache === 'only-if-cached' && !isStaticAsset
  if (isHttp && !isDevServerRequest && !skipBecauseUncached) {
    event.respondWith(
      (async () => {
        const cachedAsset = isStaticAsset && (await caches.match(event.request))
        return cachedAsset || fetchAndCache(event.request)
      })()
    )
  }
})
