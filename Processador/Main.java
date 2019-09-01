public class Main {
    static int PC; // program counter holds address of next instr
    static int AC; // the accumulator - a register for doing arithmetic
    static boolean run_bit = true; // a bit that can be turned off to halt the machine
    static int instr; // a holding register for the current instruction
    static int instr_type; // the instruction type (opcode)
    static int data_loc; // the address of the data, or -1 if none
    static int data; // holds the current operand
    static final int CLR = 90;// <-- set the value in the AC to 0
    static final int ADDI = 95;// <-- add the value x to the AC
    static final int ADDM = 93;// <-- add the value in memory location y to AC
    static final int HALT = 100;// <-- instruction which halts the processor
    // ------------------------------------------------------------------
    // This procedure interprets programs for a simple machine. The machine
    // has a register AC (accumulator), used for arithmetic. The interpreter
    // keeps running until the run bit is turned off by the HALT instruction.
    // The state of a process running on this machine consists of the memory,
    // the program counter, the run bit, and the AC. The input parameters
    // consist of the memory image and the starting address.

    public static void interpret(int memory[], int starting_address) {
        PC = starting_address;
        run_bit = true;
        while (run_bit) {
            instr = memory[PC]; // fetch next instruction into instr
            PC = PC + 1; // increment program counter
            instr_type = get_instr_type(instr); // determine instruction type
            data_loc = find_data(instr, instr_type, memory); // locate data (-1 if none)
            if (data_loc >= 0) { // if data_loc is -1, there is no operand
                data = memory[data_loc]; // fetch the data
            }
            execute(instr_type, data); // execute instruction
        }
    }

    // ------------------------------------------------------------------
    // since our instruction set is so simple, we'll let the opcode and
    // instruction type be the same.
    private static int get_instr_type(int opcode) {
        return opcode;
    }

    // ------------------------------------------------------------------
    private static int find_data(int opcode, int type, int[] memory) {
        if (opcode == ADDI) {
            return PC;
        }
        if (opcode == ADDM) {
            return memory[PC];
        } else
            return -1;
    }

    // ------------------------------------------------------------------
    private static void execute(int instr_type, int data) {
        if (instr_type == CLR) {
            AC = 0;
            System.out.println(AC);
        }
        if (instr_type == ADDI) {
            AC = AC + data;
            System.out.println(AC);
        }
        if (instr_type == ADDM) {
            AC = AC + data;
            System.out.println(AC);
        }
        if (instr_type == HALT) {
            run_bit = false;
        }
    }

    // ----------------------------------------------------------------------
    public static void main(String[] args) {
        int m2[] = { 2, -5, 15, CLR, // "program" starts here
                ADDI, 12, ADDI, 7, ADDM, 0, ADDM, 1, CLR, HALT };
        System.out.println("Memory image 1: ");
        interpret(m2, 3);// start at CLR

        int m3[] = { 1, 3, 5, CLR, // "program" starts here
                ADDI, 7, ADDM, 2, CLR, ADDM, 0, ADDM, 1, CLR, HALT };
        System.out.println("Memory image 2: ");
        interpret(m3, 3); // start at CLR

        int m4[] = { 13, -5, 7, 8, CLR, // "program" starts here
                ADDM, 1, 3, ADDM, 1, CLR, HALT };
        System.out.println("Memory image 3: ");
        interpret(m4, 4);
    }
}