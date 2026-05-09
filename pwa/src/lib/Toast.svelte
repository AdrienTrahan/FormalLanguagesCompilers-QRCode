<script lang="ts">
    import { fly } from 'svelte/transition';

    export let message: string = '';
    export let type: 'error' | 'success' = 'error';
    export let visible: boolean = false;

    const bgColor = type === 'error' ? 'bg-red-500' : 'bg-green-500';
</script>

{#if visible}
    <div
        transition:fly={{ y: 20, duration: 300 }}
        class="absolute bottom-4 left-4 right-4 z-50 {bgColor} text-white px-4 py-2 rounded-md shadow-lg"
    >
        {message}
    </div>
{/if}