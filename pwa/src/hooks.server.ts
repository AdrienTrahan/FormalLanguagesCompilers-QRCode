import { redirect } from '@sveltejs/kit';
import type { Handle } from '@sveltejs/kit';

import { base } from '$app/paths';

export const handle: Handle = async ({ event, resolve }) => {
    const response = await resolve(event);
    if (response.status === 404) throw redirect(307, `${base}/generate/`);
    return response;
};
