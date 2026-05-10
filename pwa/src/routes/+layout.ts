import { base } from '$app/paths';
import { basePath } from '$lib/path.js';
import { JavaRuntime } from '$lib/runtime';

export const prerender = true;
export const ssr = false;
export const trailingSlash = 'always';

export function load() {
    basePath.set(base);
    console.log(base);

    return {
        runtime: JavaRuntime.getInstance(),
    };
}
