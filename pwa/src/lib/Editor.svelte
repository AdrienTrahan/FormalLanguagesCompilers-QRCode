<script lang="ts">
    import { onMount, onDestroy } from 'svelte';
    import * as monaco from 'monaco-editor';
    import editorWorker from 'monaco-editor/esm/vs/editor/editor.worker?worker';

    self.MonacoEnvironment = {
        getWorker(_: unknown, label: string) {
            return new editorWorker();
        },
    };

    export let value = '';
    export let placeholder = 'start typing here';

    let container: HTMLDivElement;
    let editor: monaco.editor.IStandaloneCodeEditor;
    let showPlaceholder = true;

    onMount(() => {
        editor = monaco.editor.create(container, {
            value,
            language: 'plaintext',
            theme: 'vs',
            minimap: { enabled: false },
            lineNumbers: (ln) =>
                '<span style="margin-right:8px;" class="text-gray-300">' +
                ln +
                '</span>',
            glyphMargin: false,
            folding: false,
            lineDecorationsWidth: 0,
            lineNumbersMinChars: 3,
            automaticLayout: true,
            scrollBeyondLastLine: false,
            wordWrap: 'on',
            fontSize: 16,
            fontFamily: 'monospace',
            renderLineHighlight: 'none',
            overviewRulerLanes: 0,
            hideCursorInOverviewRuler: true,
            overviewRulerBorder: false,
            scrollbar: {
                vertical: 'auto',
                horizontal: 'auto',
                verticalScrollbarSize: 10,
                horizontalScrollbarSize: 10,
            },
            padding: { top: 16, bottom: 16 },
        });

        showPlaceholder = value === '';

        editor.onDidChangeModelContent(() => {
            value = editor.getValue();
            showPlaceholder = value === '';
        });

        editor.onDidFocusEditorText(() => {
            showPlaceholder = value === '';
        });

        editor.onDidBlurEditorText(() => {
            showPlaceholder = value === '';
        });
    });

    onDestroy(() => {
        editor?.dispose();
    });

    $: if (editor && value !== editor.getValue()) {
        editor.setValue(value);
    }
</script>

<div class="relative w-full h-full">
    <div bind:this={container} class="w-full h-full"></div>
    {#if showPlaceholder}
        <div
            class="absolute top-0 left-4 pointer-events-none text-gray-300 select-none"
            style="padding: 16px; font: 16px monospace; line-height: 24px;"
        >
            {placeholder}
        </div>
    {/if}
</div>
