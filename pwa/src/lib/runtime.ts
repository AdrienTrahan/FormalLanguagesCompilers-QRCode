import type { Screen } from "./screen";

export class JavaRuntime {
    private resolveCompiler: ((value: string) => void) | undefined;
    private resolveRunningContext: (() => void) | undefined;
    private runningContext: any;
    private compileQueue: Promise<any> = Promise.resolve();
    private runQueue: Promise<any> = Promise.resolve();
    private screen: Screen | undefined;
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
                Java_Compiler_sendOutput: runtime.sendOutput.bind(runtime),
                Java_parsing_RunningContext_clearMemory:
                    runtime.clearMemory.bind(runtime),
                Java_parsing_RunningContext_clearScreen:
                    runtime.clearScreen.bind(runtime),
                Java_parsing_RunningContext_button:
                    runtime.button.bind(runtime),
                Java_parsing_RunningContext_getMemoryAt:
                    runtime.getMemoryAt.bind(runtime),
                Java_parsing_RunningContext_getMemorySize:
                    runtime.getMemorySize.bind(runtime),
                Java_parsing_RunningContext_addToMemory:
                    runtime.addToMemory.bind(runtime),
                Java_parsing_RunningContext_display:
                    runtime.display.bind(runtime),
                Java_Runner_setRunningContext:
                    runtime.setRunningContext.bind(runtime),
            },
        });
        return runtime;
    }
    // async nativeSetApplication(lib: any, myApplication: any) {
    //     this.runningContext = myApplication;
    //     console.log("Java application instance set on JavaScript side.");

    //     setTimeout(() => {
    //         this.runningContext.queueBlockName("asd");
    //     }, 1000);
    //     return new Promise(() => {});
    // }

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

    async run(bytecode: string, screen: Screen): Promise<void> {
        const task = this.runQueue.then(() => this.runRunner(bytecode, screen));
        this.runQueue = task.catch(() => {});
        return task;
    }

    private async runRunner(bytecode: string, screen: Screen) {
        this.screen = screen;
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

    async runBlock(blockName: string) {
        if (this.runningContext)
            await this.runningContext.queueBlockName(blockName);
    }

    async sendOutput(lib: any, str: any) {
        this.resolveCompiler?.(str);
    }

    async setRunningContext(lib: any, runningContext: any) {
        this.runningContext = runningContext;
        return new Promise<void>(
            (resolve) => (this.resolveRunningContext = resolve),
        );
    }

    async clearMemory() {
        localStorage.removeItem("memory");
    }

    async clearScreen() {
        if (this.screen) this.screen.clearScreen();
    }

    async button(lib: any, value: string, blockName: string) {
        value = await toJsPrimitive(value);
        blockName = await toJsPrimitive(blockName);
        if (this.screen)
            this.screen.addButton(value, () => {
                this.runBlock(blockName);
            });
    }

    async display(lib: any, value: string) {
        value = await toJsPrimitive(value);
        if (this.screen) this.screen.display(value);
    }

    async getMemoryAt(lib: any, index: number) {
        index = await toJsPrimitive(index);

        if (localStorage.getItem("memory") == null)
            localStorage.setItem("memory", JSON.stringify([]));
        const memory = JSON.parse(localStorage.getItem("memory")!);
        return memory[index];
    }

    async getMemorySize() {
        if (localStorage.getItem("memory") == null)
            localStorage.setItem("memory", JSON.stringify([]));
        const memory = JSON.parse(localStorage.getItem("memory")!);
        return memory.length;
    }

    async addToMemory(lib: any, value: any) {
        console.log("here");

        value = await toJsPrimitive(value);
        if (localStorage.getItem("memory") == null)
            localStorage.setItem("memory", JSON.stringify([]));

        const memory = JSON.parse(localStorage.getItem("memory")!);
        memory.push(value);
        localStorage.setItem("memory", JSON.stringify(memory));
    }

    endRunningLoop() {
        this.resolveRunningContext?.();
        this.runningContext = undefined;
    }
}

async function toJsPrimitive(value: any): Promise<any> {
    if (value == null) return null;
    const t = typeof value;
    if (t !== "object") return value;
    if (typeof value.booleanValue === "function")
        return value.booleanValue() === true;
    if (typeof value.doubleValue === "function") return value.doubleValue();
    if (typeof value.floatValue === "function") return value.floatValue();
    if (typeof value.intValue === "function") return value.intValue();
    if (typeof value.longValue === "function") return Number(value.longValue());
    if (typeof value.toString === "function") return value.toString();
    return value;
}
