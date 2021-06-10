import java.util.Scanner;

public class Simulator {

    public static void main(String[] args) {
        InstructionDecode.createRegisters();
        Scanner sc = new Scanner(System.in);

        int NumberOfInstructions = 6;
        String[] Instructions = new String[NumberOfInstructions];

        for (int i = 0; i < NumberOfInstructions; ++i) {
            Instructions[i] = sc.nextLine();
        }


        int limit = NumberOfInstructions + 4;
        for (int i = 1; i <= limit; ++i) {
            System.out.println();
            System.out.println();
            System.out.println("PC " + i +" Started===========================================================");

            //WriteBack
            if (i > 4 && i <= NumberOfInstructions+4) {
                WriteBackClass.WriteBack(InstructionDecode.RegDst, Execution.ALUresult, "", InstructionDecode.MemToReg);
                System.out.println("Entered WriteBack Stage ////////////////////////////////////");
            }
            //Memory
            if (i > 3 && i <= NumberOfInstructions+3) {
                MemoryAccess.MemAccess(Execution.ALUresult, InstructionDecode.readData2, InstructionDecode.SignExtend(InstructionDecode.immediate), Execution.zeroFlag, Execution.BranchAddressResult, InstructionDecode.MemWrite, InstructionDecode.MemRead, InstructionDecode.PCSrc);
                System.out.println("Entered Memory Stage ////////////////////////////////////");
            }
            //Excute
            if (i > 2 && i <= NumberOfInstructions+2) {
                Execution.Execute(InstructionDecode.ALUOp, InstructionDecode.ALUSrc, InstructionDecode.readData1, InstructionDecode.readData2, "");
                System.out.println("Entered Excute Stage ////////////////////////////////////");
            }
            //Decode
            if (i > 1 && i <= NumberOfInstructions+1) {
                InstructionDecode.InstDecode(InstructionFetch.instruction);
                System.out.println("Entered Decode Stage ////////////////////////////////////");
                System.out.println("EXECUTION RESULT: " + Execution.ALUresult );
                System.out.println("BRANCH ADDRESS RESULT: " + Execution.BranchAddressResult);
                System.out.println("ZERO FLAG: " + Execution.zeroFlag);
            }
            //Fetch

            if (i <= NumberOfInstructions) {
                InstructionFetch.loadInstruction(Instructions[i-1]);
                InstructionFetch.InstFetch(InstructionFetch.pc);
                System.out.println("Entered Fetch Stage ////////////////////////////////////");
            }
            System.out.println("PC " + i +" Finished===========================================================");

        }

/*
opcode | rs | rt | rd | shamt | funct
0000000000.00010.00011.00100.00000.00 /add
opcode | rs | rt | rd | shamt | funct
0000000000.00110.00101.00111.00000.10 /sub bdal or 3ashan mfeesh or fel instructions ele m5trenha
opcode | rs | rt | immediate
0000000010.00100.00001.000000000000 / lw
opcode | rs | rt | immediate
0000000011.00001.00010.000000000000 / sw
opcode | rs | rt | immediate
0000000100.01000.00000.000000000000 /beq


00000000000001000011001000000000 /ADD
00000000100010000001000000000000 /LW
00000000000011000101001110000010 /SUB
00000000000011000101001110000010 /SUB
00000000110000100010000000000000 /SW
00000001000100000000000000000000 /BEQ
*/
    }
}
