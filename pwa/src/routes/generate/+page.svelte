<script lang="ts">
    import Editor from '$lib/Editor.svelte';
    import Toast from '$lib/Toast.svelte';
    import QRCode from 'qrcode';
    import { writable, type Writable } from 'svelte/store';

    let code = '';
    let loading: Writable<boolean> = writable(false);
    let toastMessage = '';
    let toastVisible = false;
    let qrCodeUrl = '';
    let showQR = false;
    export let data;
    const { runtime: runtimePromise } = data;

    function showError(msg: string) {
        toastMessage = msg;
        toastVisible = true;
        setTimeout(() => {
            toastVisible = false;
        }, 5000);
    }

    async function generateQRCode() {
        loading.set(true);
        const runtime = await runtimePromise;
        runtime
            .compile(code)
            .then(async (bytecode) => {
                loading.set(false);
                const qrData = JSON.stringify(bytecode);
                qrCodeUrl = await QRCode.toDataURL(qrData, {
                    width: 300,
                    margin: 2,
                });
                showQR = true;
            })
            .catch((err) => {
                loading.set(false);
                showError(err?.message || String(err));
            });
    }

    function closeQR() {
        showQR = false;
    }
</script>

<div class="h-full w-full relative">
    <Toast message={toastMessage} type="error" visible={toastVisible} />
    <Editor bind:value={code} />
    <button
        on:click={generateQRCode}
        class="bg-blue-600 text-white absolute right-2 top-2 font-bold px-8 py-2 rounded-sm cursor-pointer hover:bg-blue-700 transition-all active:bg-blue-900 disabled:opacity-50"
        disabled={$loading}
    >
        {#if $loading}
            <svg class="animate-spin h-5 w-5 mx-auto" viewBox="0 0 24 24">
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
        {:else}GENERATE{/if}
    </button>
    {#if $loading}
        <div
            class="absolute inset-0 bg-white/50 flex items-center justify-center"
        >
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
    {/if}
    {#if showQR}
        <!-- svelte-ignore a11y-click-events-have-key-events -->
        <!-- svelte-ignore a11y-no-static-element-interactions -->
        <div
            class="fixed inset-0 bg-black/40 flex items-center justify-center z-50"
            on:click={closeQR}
        >
            <!-- svelte-ignore a11y-click-events-have-key-events -->
            <!-- svelte-ignore a11y-no-static-element-interactions -->
            <div class="bg-white p-4 rounded-lg" on:click|stopPropagation>
                <img src={qrCodeUrl} alt="QR Code" class="max-w-full" />
                <button
                    class="mt-4 w-full bg-blue-600 text-white font-bold px-8 py-2 rounded-sm cursor-pointer hover:bg-blue-700 transition-all active:bg-blue-900"
                    on:click={closeQR}>CLOSE</button
                >
            </div>
        </div>
    {/if}
</div>
