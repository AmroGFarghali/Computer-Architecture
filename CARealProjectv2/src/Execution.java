public class Execution {
	static String ALUresult = "00";
	static int zeroFlag = 0;
	static String BranchAddressResult = "00"; // (SIGN EXTEND * 4) + NEW PC
	
	public static void Execute(String ALUOp, int ALUSrc, String readData1, String readData2, String signExtend) {
		switch(ALUOp) {

			case "000": { // ARITHMETIC
				switch(Integer.parseInt(InstructionDecode.funct,2)) {
					case 0: { // ADD
						if(InstructionDecode.ALUSrc == 0)
							ALUresult = Integer.toBinaryString(addOp(Integer.parseInt(readData1, 2), Integer.parseInt(readData2, 2)));
						else
							ALUresult = Integer.toBinaryString(addOp(Integer.parseInt(readData1, 2), Integer.parseInt(InstructionDecode.immediate, 2)));
						zeroFlag = Integer.parseInt(ALUresult, 2) == 0 ? 1 : 0;
						break;
					}
					case 1: { // ADDI
						if(InstructionDecode.ALUSrc == 0)
							ALUresult = Integer.toBinaryString(ADDIOp(Integer.parseInt(readData1, 2), InstructionDecode.rt));
						else {
							ALUresult = Integer.toBinaryString(ADDIOp(Integer.parseInt(readData1, 2), Integer.parseInt(InstructionDecode.immediate, 2)));
						zeroFlag = Integer.parseInt(ALUresult, 2) == 0 ? 1 : 0;}
						break;
					}
					case 2: { // SUB
						if(InstructionDecode.ALUSrc == 0)
							ALUresult = Integer.toBinaryString(subOp(Integer.parseInt(readData1, 2), Integer.parseInt(readData2, 2)));
						else	
							ALUresult = Integer.toBinaryString(subOp(Integer.parseInt(readData1, 2), Integer.parseInt(InstructionDecode.immediate, 2)));
						zeroFlag = Integer.parseInt(ALUresult, 2) == 0 ? 1 : 0;
						break;
					}
					case 3: { // MULTIPLY
						if(InstructionDecode.ALUSrc == 0)
							ALUresult = Integer.toBinaryString(MULTOp(Integer.parseInt(readData1, 2), Integer.parseInt(readData2, 2)));
						else
							ALUresult = Integer.toBinaryString(MULTOp(Integer.parseInt(readData1, 2), Integer.parseInt(InstructionDecode.immediate, 2)));
						zeroFlag = Integer.parseInt(ALUresult, 2) == 0 ? 1 : 0;
						break;
					}
					default: break;
				}
				break;
			}
			case "001" : { // LOGICAL
				switch(Integer.parseInt(InstructionDecode.funct,2)) {
					case 0: { // AND
						if(InstructionDecode.ALUSrc == 0)
							ALUresult = Integer.toBinaryString(ANDOp(Integer.parseInt(readData1, 2), Integer.parseInt(readData2, 2)));
						else
							ALUresult = Integer.toBinaryString(ANDOp(Integer.parseInt(readData1, 2), Integer.parseInt(InstructionDecode.immediate, 2)));
						zeroFlag = Integer.parseInt(ALUresult, 2) == 0 ? 1 : 0;
						break;
					}
					case 1: { // ORI
						if(InstructionDecode.ALUSrc == 0)
							ALUresult = Integer.toBinaryString(OROp(Integer.parseInt(readData1, 2), InstructionDecode.rt));
						else
							ALUresult = Integer.toBinaryString(OROp(Integer.parseInt(readData1, 2), Integer.parseInt(InstructionDecode.immediate, 2)));
						zeroFlag = Integer.parseInt(ALUresult, 2) == 0 ? 1 : 0;
						break;
					}
					case 2: { // SLL
						ALUresult = SLLOp(readData1);
						zeroFlag = Integer.parseInt(ALUresult, 2) == 0 ? 1 : 0;
						break;
					}
					case 3: { // SRL
						ALUresult = SRLOp(readData1);
						zeroFlag = Integer.parseInt(ALUresult, 2) == 0 ? 1 : 0;
						break;
					}
					default: break;
				}
				break;
			}
			case "010" : { // LW SW
				if(InstructionDecode.ALUSrc == 0)
					ALUresult = Integer.toBinaryString(addOp(Integer.parseInt(readData1, 2), Integer.parseInt(readData2, 2)));
				else 
					ALUresult = Integer.toBinaryString(addOp(Integer.parseInt(readData1, 2), Integer.parseInt(InstructionDecode.immediate, 2)));
				zeroFlag = Integer.parseInt(ALUresult, 2) == 0 ? 1 : 0;
				BranchAddressResult = Integer.toBinaryString((Integer.parseInt(InstructionDecode.SignExtend(InstructionDecode.immediate),2) * 4) + Integer.parseInt(InstructionFetch.pc));
				break;
			}
			case "011": { // BEQ BNE
				if(InstructionDecode.ALUSrc == 0)
					ALUresult = Integer.toBinaryString(subOp(Integer.parseInt(readData1, 2), Integer.parseInt(readData2, 2)));
				else 
					ALUresult = Integer.toBinaryString(subOp(Integer.parseInt(readData1, 2), Integer.parseInt(InstructionDecode.immediate, 2)));
				zeroFlag = Integer.parseInt(ALUresult, 2) == 0 ? 1 : 0;
				BranchAddressResult = Integer.toBinaryString((Integer.parseInt(InstructionDecode.SignExtend(InstructionDecode.immediate),2) * 4) + Integer.parseInt(InstructionFetch.pc));
				break;
			}
			case "100": { // SLT
				ALUresult = Integer.toBinaryString(sltOp(Integer.parseInt(readData1, 2), Integer.parseInt(readData2, 2)));
				zeroFlag = Integer.parseInt(ALUresult, 2) == 0 ? 1 : 0;
				break;
			}
			default: break;
		}
		System.out.println("NEXT PC: " + InstructionFetch.pc);
	}
	
	public static int ANDOp(int Operand1, int Operand2) {return (Operand1 & Operand2);}
	public static int OROp(int Operand1, int Operand2) {return (Operand1 | Operand2);}
	public static int addOp(int Operand1, int Operand2) {return (Operand1 + Operand2);}
	public static int subOp(int Operand1, int Operand2) {return (Operand1 - Operand2);}
	public static int sltOp(int Operand1, int Operand2) {return (Operand1 < Operand2) ? 1 : 0;}
	public static int NOR(int Operand1, int Operand2) {return ~(Operand1 | Operand2);}
	public static int MULTOp(int Operand1, int Operand2) {return (Operand1 * Operand2);}
	public static int ADDIOp(int Operand1, int Operand2) {return (Operand1 + Operand2);}
	public static String SLLOp(String operand) {
		String res;
		res = operand.substring(InstructionDecode.shamt);
		for(int i = 0; i < InstructionDecode.shamt; i++) {
			res = res.concat("0");
		}
		return res;
	}
	public static String SRLOp(String operand) {
		String res;
		res = operand.substring(0, (32 - InstructionDecode.shamt));
		for(int i = 0; i < InstructionDecode.shamt; i++) {
			res = "0".concat(res);
		}
		return res;
	}
}
/*
000000000000000101101110100
000000000100010010111010101
000000000000001010101010100
000000000100011101010101001


00000
00001
00010
00011


 */


