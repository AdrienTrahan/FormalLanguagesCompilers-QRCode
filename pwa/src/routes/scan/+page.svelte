<script lang="ts">
    import { onMount, onDestroy } from 'svelte';
    import { Html5Qrcode } from 'html5-qrcode';
    import Playground from '$lib/Playground.svelte';

    let scanner: Html5Qrcode | null = null;
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
        if (scanner) {
            try {
                await scanner.stop();
            } catch {}
            scanner = null;
        }
        await new Promise(r => setTimeout(r, 100));
        try {
            scanner = new Html5Qrcode('reader');
            await scanner.start(
                { facingMode: 'environment' },
                {
                    fps: 10,
                },
                (decodedText) => {
                    try {
                        bytecode = JSON.parse(decodedText);
                        showPlayground = true;
                        stopScanner();
                    } catch {
                        error = 'Invalid QR code format';
                    }
                },
                () => {},
            );
            scanning = true;
        } catch (err) {
            error = 'Could not start camera. Please grant camera permission.';
            console.error(err);
        }
    }

    async function stopScanner() {
        if (scanner && scanning) {
            try {
                await scanner.stop();
                scanning = false;
            } catch {
                // ignore
            }
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
            <div
                id="reader"
                class="w-full h-full [&_video]:w-full [&_video]:h-full [&_video]:object-contain"
            ></div>
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
