export class Parameters {
    public readonly in: string;
    public readonly out: string;
    public readonly verbose: boolean;

    public constructor(args: string[]) {
        let needAssignedIn = true;
        let inTemp = "input.txt";
        let needAssignedOut = true;
        let outTemp = "output.txt";
        let verboseTemp = false;
        for (let i = 0; i < args.length; i += 1) {
            if (needAssignedIn && args[i] === "-in") {
                if (i < args.length - 1) {
                    inTemp = args[i + 1];
                    needAssignedIn = false;
                    i += 1;
                }
            } else if (needAssignedOut && args[i] === "-out") {
                if (i < args.length - 1) {
                    outTemp = args[i + 1];
                    needAssignedOut = false;
                    i += 1;
                }
            } else if (!verboseTemp && args[i] === "-verbose") {
                verboseTemp = true;
            }
        }
        this.in = inTemp;
        this.out = outTemp;
        this.verbose = verboseTemp;
    }
}
