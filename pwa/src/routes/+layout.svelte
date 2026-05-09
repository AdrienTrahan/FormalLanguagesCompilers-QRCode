<script lang="ts">
    import { cn } from '@sglara/cn';
    import { page } from '$app/stores';
    import '../app.css';
    export let data;
    const { runtime } = data;
</script>

<div class="fixed inset-0 flex flex-col">
    <div
        class="flex select-none font-bold justify-center items-center h-12 border-b-zinc-200 border-b"
    >
        PASSPORT ZOO
    </div>
    <div class="flex-1 min-h-0">
        <slot />
    </div>

    <div class="flex select-none font-bold justify-center items-center h-14">
        <a
            href="/generate/"
            class={cn(
                'flex-1 cursor-pointer h-full flex items-center justify-center border-t-2',
                $page.url.pathname === '/generate/'
                    ? 'text-blue-600'
                    : 'text-zinc-500',
                $page.url.pathname === '/generate/'
                    ? 'border-t-blue-600'
                    : 'border-t-zinc-100',
            )}>Generate</a
        >
        <a
            href="/scan/"
            class={cn(
                'flex-1 cursor-pointer h-full flex items-center justify-center border-t-2 ',
                $page.url.pathname === '/scan/'
                    ? 'text-blue-600'
                    : 'text-zinc-500',
                $page.url.pathname === '/scan/'
                    ? 'border-t-blue-600'
                    : 'border-t-zinc-100',
            )}>Scan</a
        >
    </div>
</div>
