import { writable, type Writable } from "svelte/store";

export class Screen {
    lines: Writable<string[]> = writable([]);
    buttons: Writable<[string, () => void][]> = writable([]);

    addButton(text: string, callback: () => void) {
        this.buttons.update((buttons) => [...buttons, [text, callback]]);
    }
    display(text: string) {
        this.lines.update((lines) => [...lines, text]);
    }
    clearScreen() {
        this.lines.set([]);
        this.buttons.set([]);
    }
}
