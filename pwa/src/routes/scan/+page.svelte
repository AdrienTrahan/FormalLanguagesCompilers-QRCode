<script lang="ts">
    import { onMount, onDestroy } from 'svelte';
    import { BrowserMultiFormatReader } from '@zxing/browser';
    import Playground from '$lib/Playground.svelte';

    let codeReader: BrowserMultiFormatReader | null = null;
    let controls: { stop: () => void } | null = null;
    let scanning = false;
    let error = '';
    let showPlayground = false;
    let bytecode: string | null = null;

    export let data;
    const { runtime: runtimePromise } = data;

    onMount(() => {
        startScanner();
    });

    onDestroy(() => {
        stopScanner();
    });

    async function startScanner() {
        error = '';
        if (controls) {
            controls.stop();
            controls = null;
        }
        await new Promise(r => setTimeout(r, 100));
        try {
            codeReader = new BrowserMultiFormatReader();
            controls = await codeReader.decodeFromVideoDevice(
                undefined,
                'reader',
                (result) => {
                    if (result) {
                        try {
                            bytecode = JSON.parse(result.getText());
                            showPlayground = true;
                            stopScanner();
                        } catch {
                            error = 'Invalid QR code format';
                        }
                    }
                }
            );
            scanning = true;
        } catch (err) {
            error = 'Could not start camera. Please grant camera permission.';
            console.error(err);
        }
    }

    function stopScanner() {
        if (controls) {
            controls.stop();
            controls = null;
            scanning = false;
        }
    }

    function closePlayground() {
        showPlayground = false;
        bytecode = null;
        startScanner();
    }
</script>

<div class="h-full w-full relative">
    {#if showPlayground && bytecode}
        <Playground {bytecode} {runtimePromise} on:close={closePlayground} />
    {:else}
        <div class="absolute inset-0">
            <video
                id="reader"
                class="w-full h-full object-contain"
            ><track kind="captions" /></video>
            {#if error}
                <div class="absolute inset-0 flex items-center justify-center">
                    <div class="bg-red-500 text-white px-4 py-2 rounded-md">
                        {error}
                    </div>
                </div>
            {/if}
        </div>
    {/if}
</div>