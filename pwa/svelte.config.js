import { vitePreprocess } from '@sveltejs/vite-plugin-svelte';
import adapter from '@sveltejs/adapter-static';

/** @type {import('@sveltejs/kit').Config} */
const config = {
    preprocess: vitePreprocess(),
    kit: {
        adapter: adapter({
            assets: '../docs',
            pages: '../docs',
            fallback: 'index.html',
        }),
        paths: {
            base: '/FormalLanguagesCompilers-QRCode',
        },
    },
};

export default config;
