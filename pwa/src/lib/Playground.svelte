<script lang="ts">
    import { createEventDispatcher, onMount } from 'svelte';
    import { JavaRuntime } from '$lib/runtime';
    import { writable, type Writable } from 'svelte/store';
    import { Screen } from './screen';
    import Toast from '$lib/Toast.svelte';

    export let bytecode: string;
    export let runtimePromise: Promise<JavaRuntime>;
    export let loading: Writable<boolean> = writable(true);

    const localLoading = writable(true);
    const screen = new Screen();
    const dispatch = createEventDispatcher();
    const lines = screen.lines;
    const buttons = screen.buttons;
    let toastMessage = '';
    let toastVisible = false;

    function showError(msg: string) {
        toastMessage = msg;
        toastVisible = true;
        setTimeout(() => {
            toastVisible = false;
        }, 5000);
    }

    onMount(() => {
        run();
        return async () => {
            const runtime = await runtimePromise;
            runtime.endRunningLoop();
        };
    });
    async function run() {
        try {
            const runtime = await runtimePromise;
            localLoading.set(false);
            loading.set(false);
            runtime.run(bytecode, screen).catch((err: unknown) => {
                showError(err instanceof Error ? err.message : String(err));
            });
        } catch (err: unknown) {
            localLoading.set(false);
            loading.set(false);
            showError(err instanceof Error ? err.message : String(err));
        }
    }
    async function close() {
        try {
            const runtime = await runtimePromise;
            runtime.endRunningLoop();
        } catch {}
        dispatch('close');
    }
</script>

<Toast message={toastMessage} type="error" visible={toastVisible} />

<div class="absolute inset-0 bg-white flex flex-col">
    {#if $localLoading}
        <div class="flex-1 flex items-center justify-center">
            <svg class="animate-spin h-8 w-8 text-blue-600" viewBox="0 0 24 24">
                <circle
                    class="opacity-25"
                    cx="12"
                    cy="12"
                    r="10"
                    stroke="currentColor"
                    stroke-width="4"
                    fill="none"
                />
                <path
                    class="opacity-75"
                    fill="currentColor"
                    d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4z"
                />
            </svg>
        </div>
    {:else}
        <div class="flex-1 min-h-0 flex flex-col">
            <div class="flex-1 p-4 flex flex-col gap-2 min-h-0">
                {#each $lines as line}
                    <p class="text-xl">{line}</p>
                {/each}
            </div>
            {#if $buttons.length > 0}
                <div class="p-4 border-t border-t-gray-200 flex-col flex gap-1">
                    {#each $buttons as [text, callback]}
                        <button
                            class="w-full bg-zinc-500 text-white font-bold px-8 py-2 rounded-sm cursor-pointer hover:bg-zinc-400 transition-all active:bg-zinc-300"
                            on:click={callback}>{text}</button
                        >
                    {/each}
                </div>
            {/if}
        </div>
    {/if}
    <div class="p-4 border-t border-t-gray-200">
        <button
            class="w-full bg-blue-600 text-white font-bold px-8 py-2 rounded-sm cursor-pointer hover:bg-blue-700 transition-all active:bg-blue-900"
            on:click={close}>SCAN AGAIN</button
        >
    </div>
</div>
