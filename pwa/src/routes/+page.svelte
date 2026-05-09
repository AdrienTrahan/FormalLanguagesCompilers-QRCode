<script lang="ts">
    import { JavaRuntime } from '$lib/runtime'
    import { onMount } from 'svelte'

    onMount(async () => {
        JavaRuntime.getInstance().then(async (runtime) => {
            const bytecode = await runtime.compile("function root({});");
            await runtime.run(bytecode);
            console.log(bytecode);
        })
    })
</script>
