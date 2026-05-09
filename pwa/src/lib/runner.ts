async function Java_Runner_clearMemory() {}

async function Java_Runner_clearScreen() {}

async function Java_Runner_button(value: any, goto: string) {}

async function Java_Runner_display(value: any) {}

async function Java_Runner_getMemoryAt(index: number) {}

async function Java_Runner_getMemorySize() {}

async function Java_Runner_addToMemory(value: any) {}

export async function run(bytecode: string) {
    return await new Promise<void>(async (resolve, reject) => {
        await (window as any).cheerpjInit({
            version: 17,
            natives: {
                Java_Runner_clearMemory,
                Java_Runner_clearScreen,
                Java_Runner_button,
                Java_Runner_display,
                Java_Runner_getMemoryAt,
                Java_Runner_getMemorySize,
                Java_Runner_addToMemory,
            },
        });
        const exitCode = await (window as any).cheerpjRunJar(
            "/app/runtime/Runner.jar",
            [bytecode],
        );

        if (exitCode != 0)
            reject(new Error(`Runner.jar exited with code ${exitCode}`));
        else resolve();
    });
}
