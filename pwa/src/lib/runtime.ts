export class JavaRuntime {
    private resolveCompiler: ((value: string) => void) | undefined;
    private compileQueue: Promise<any> = Promise.resolve();
    private runQueue: Promise<any> = Promise.resolve();
    private static instance: JavaRuntime;

    static async getInstance(): Promise<JavaRuntime> {
        if (!JavaRuntime.instance)
            JavaRuntime.instance = await JavaRuntime.create();
        return JavaRuntime.instance;
    }

    static async create(): Promise<JavaRuntime> {
        const runtime = new JavaRuntime();
        await (window as any).cheerpjInit({
            version: 17,
            natives: {
                Java_Compiler_sendOutput:
                    runtime.Java_Compiler_sendOutput.bind(runtime),
                Java_Runner_clearMemory:
                    runtime.Java_Runner_clearMemory.bind(runtime),
                Java_Runner_clearScreen:
                    runtime.Java_Runner_clearScreen.bind(runtime),
                Java_Runner_button: runtime.Java_Runner_button.bind(runtime),
                Java_Runner_display: runtime.Java_Runner_display.bind(runtime),
                Java_Runner_getMemoryAt:
                    runtime.Java_Runner_getMemoryAt.bind(runtime),
                Java_Runner_getMemorySize:
                    runtime.Java_Runner_getMemorySize.bind(runtime),
                Java_Runner_addToMemory:
                    runtime.Java_Runner_addToMemory.bind(runtime),
            },
        });
        return runtime;
    }
    async compile(code: string): Promise<string> {
        const task = this.compileQueue.then(() => this.runCompile(code));
        this.compileQueue = task.catch(() => {});
        return task;
    }

    private async runCompile(code: string): Promise<string> {
        return new Promise<string>(async (resolve, reject) => {
            this.resolveCompiler = resolve;
            try {
                const exitCode = await (window as any).cheerpjRunJar(
                    "/app/runtime/Compiler.jar",
                    [code],
                );

                if (exitCode !== 0)
                    reject(
                        new Error(`Compiler.jar exited with code ${exitCode}`),
                    );
            } catch (err) {
                reject(err);
            }
        });
    }

    async run(bytecode: string): Promise<void> {
        const task = this.runQueue.then(() => this.runRunner(bytecode));
        this.runQueue = task.catch(() => {});
        return task;
    }

    private async runRunner(bytecode: string) {
        return await new Promise<void>(async (resolve, reject) => {
            const exitCode = await (window as any).cheerpjRunJar(
                "/app/runtime/Runner.jar",
                [bytecode],
            );

            if (exitCode != 0)
                reject(new Error(`Runner.jar exited with code ${exitCode}`));
            else resolve();
        });
    }

    async Java_Compiler_sendOutput(lib: any, str: any) {
        this.resolveCompiler?.(str);
    }

    async Java_Runner_clearMemory() {}

    async Java_Runner_clearScreen() {}

    async Java_Runner_button(value: any, goto: string) {}

    async Java_Runner_display(value: any) {}

    async Java_Runner_getMemoryAt(index: number) {}

    async Java_Runner_getMemorySize() {}

    async Java_Runner_addToMemory(value: any) {}
}
