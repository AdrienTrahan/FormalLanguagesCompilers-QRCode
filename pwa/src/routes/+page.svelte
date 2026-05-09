<script lang="ts">
    import { JavaRuntime } from '$lib/runtime'
    import { Screen } from '$lib/screen';
    import { onMount } from 'svelte'
    const code = `
        function root({
            clearScreen();
            display("Zoo passport");
            counter = 0;
            while (counter <= getMemorySize()).then({
                display(getMemoryAt(counter));
                counter += 1;
            });

            button("New animal").then({
                animalHasPatterns();
            });
            button("clear passport").then({
                clearMemory();
                root();
            });
        });

        function animalHasPatterns({
            clearScreen();
            display("Does the animal have patterns on its skin?");
            button("Yes").then({
                animalHasLongNeck();
            });
            button("No").then({
                animalHasTrunk();
            });
            button("Show Passport").then({
                root();
            });
        });


        function animalHasTrunk({
            clearScreen();
            display("Does the animal have a trunk?");
            button("Yes").then({
                selectedAnimal = "elephant";
                addAnimal();
                root();
            });
            button("No").then({
                animalDoesNotExist();
            });
            button("Show Passport").then({
                root();
            });
        });

        function animalHasLongNeck({
            clearScreen();
            display("Does the animal have a long neck?");
            button("Yes").then({
                selectedAnimal = "giraffe";
                addAnimal();
                root();
            });
            button("No").then({
                selectedAnimal = "zebra";
                addAnimal();
                root();
            });
            button("Show Passport").then({
                root();
            });
        });


        function addAnimal({
            counter = 0;
            alreadyExists = false;
            while (counter < getMemorySize()).then({
                if (getMemoryAt(counter) == selectedAnimal).then({
                    alreadyExists = true;
                });
                counter += 1;
            });

            if (alreadyExists == false).then({
                addToMemory(selectedAnimal);
            });
        });

        function animalDoesNotExist({
            clearScreen();
            display("The animal does not exist. Keep looking.");
            button("menu").then({
                root();
            });
        });
    `;

    const screen = new Screen();
    const lines = screen.lines;
    const buttons = screen.buttons;
    onMount(async () => {
        JavaRuntime.getInstance().then(async (runtime) => {
            const bytecode = await runtime.compile(code);
            await runtime.run(bytecode, screen);
            console.log(bytecode);
        })
    })
</script>
<div>
    {#each $lines as line}
        <div>{line}</div>
    {/each}
</div>

<div>
    {#each $buttons as [text, callback]}
        <button on:click={callback}>{text} button</button>
    {/each}
</div>

