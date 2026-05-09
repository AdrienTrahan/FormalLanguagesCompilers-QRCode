import { JavaRuntime } from '$lib/runtime';

export const prerender = true;
export const ssr = false;
export const trailingSlash = 'always';

export function load({ url }) {
    return {
        runtime: JavaRuntime.getInstance(),
    };
}
