public class InstructionDecode {
	static Register[] registerFile = new Register[32];
	static int numberOfRegisters = 0;
	static String ALUOp = "00", readData1 = "00", readData2 = "00", funct = "00", immediate = "00";
	static int RegDst = 0, RegWrite = 0, ALUSrc = 0, PCSrc = 0, MemRead = 0, MemWrite = 0, MemToReg = 0, op = 0, rs = 0, rt = 0, rd = 0, shamt = 0, address = 0;
	
	public static void createRegisters() {
		while(numberOfRegisters < 32) {
			Register r = new Register(Integer.toBinaryString(numberOfRegisters));
			registerFile[numberOfRegisters] = r;
			numberOfRegisters++;
		}
		System.out.println("32 REGISTERS WERE CREATED AND ADDED TO THE REGISTER FILE.");
	}
	
	public static void InstDecode(String inst) {
		if(!inst.equalsIgnoreCase("F")) {
			op = Integer.parseInt(inst.substring(0, 10),2);
			rs = Integer.parseInt(inst.substring(10, 15),2);
			rt = Integer.parseInt(inst.substring(15, 20),2);
			rd = Integer.parseInt(inst.substring(20, 25),2);
			funct = inst.substring(30, 32);
			immediate = inst.substring(20, 32);
			address = Integer.parseInt(inst.substring(10,32), 2);
			shamt = Integer.parseInt(inst.substring(25,30), 2);
			readData1 = registerFile[rs].getValue();
			readData2 = registerFile[rt].getValue();
			ContUnit(op);
			System.out.println("SIGNALS:");
			System.out.println("read data 1: " + readData1);
			System.out.println("read data 2: " + readData2);
			System.out.println("sign extend: " + InstructionDecode.SignExtend(immediate));
			System.out.println("NEXT PC: " + InstructionFetch.pc);
			System.out.println("rt: " + Integer.toBinaryString(InstructionDecode.rt));
			System.out.println("rd: " + Integer.toBinaryString(InstructionDecode.rd));
			System.out.println("SIGNALS:");
			System.out.println("RegDst: " + InstructionDecode.RegDst);
			System.out.println("RegWrite: " + InstructionDecode.RegWrite);
			System.out.println("ALUSrc: " + InstructionDecode.ALUSrc);
			System.out.println("PCSrc: " + InstructionDecode.PCSrc);
			System.out.println("MemRead: " + InstructionDecode.MemRead);
			System.out.println("MemWrite: " + InstructionDecode.MemWrite);
			System.out.println("MemToReg: " + InstructionDecode.MemToReg);
		}
	}
	
	public static String SignExtend(String str) {
		String res;
		String ones = "11111111111111111111";
		String zeros = "00000000000000000000";
		if(str.charAt(0) == '1') {
			res = ones.concat(str);
		} else {
			res = zeros.concat(str);
		}
		return res;
	}
	
	public static void ContUnit(int op) {
		switch(op) {
			case 0: { // ARITHMETIC
				immediate="000000000000";
				ALUOp = "000";
				switch(Integer.parseInt(funct,2)) {
					case 0: case 2: case 3: { // ADD, MULTIPLY, SUB
						RegDst = 1;
						RegWrite = 1;
						ALUSrc = 0;
						MemRead = 0;
						MemWrite = 0;
						MemToReg = 0;
						break;
					}
					case 1: { // ADDI
						RegDst = 0;
						RegWrite = 1;
						ALUSrc = 1;
						MemRead = 0;
						MemWrite = 0;
						MemToReg = 0;
						break;
					}
					default: break;
				}
				break;
			}
			case 1: { // LOGICAL
				immediate="000000000000";
				ALUOp = "001";
				switch(Integer.parseInt(funct,2)) {
					case 0: case 2: case 3: { // AND SLL SRL
						RegDst = 1;
						RegWrite = 1;
						ALUSrc = 0;
						MemRead = 0;
						MemWrite = 0;
						MemToReg = 0;
						break;
					}
					case 1: { // ORI
						RegDst = 0;
						RegWrite = 1;
						ALUSrc = 1;
						MemRead = 0;
						MemWrite = 0;
						MemToReg = 0;
						break;
					}
					default: break;
				}
				break;
			}
			case 2: { // LW 
				ALUOp = "010";
				RegDst = 0;
				RegWrite = 1;
				ALUSrc = 1;
				MemRead = 1;
				MemWrite = 0;
				MemToReg = 1;
				break;
			}
			case 3: { // SW
				ALUOp = "010";
				RegDst = 1;
				RegWrite = 0;
				ALUSrc = 1;
				MemRead = 0;
				MemWrite = 1;
				MemToReg = 1;
				break;
			}
			case 4: { // BEQ BNE
				ALUOp = "011";
				RegDst = 1;
				RegWrite = 0;
				ALUSrc = 0;
				MemRead = 0;
				MemWrite = 0;
				MemToReg = 1;
				break;
			}
			case 5: { // SLT
				ALUOp = "100";
				RegDst = 1;
				RegWrite = 1;
				ALUSrc = 0;
				MemRead = 0;
				MemWrite = 0;
				MemToReg = 0;
				break;
			}
			case 6: { // J
				ALUOp = "101";
				RegDst = 1;
				RegWrite = 0;
				ALUSrc = 1;
				MemRead = 0;
				MemWrite = 0;
				MemToReg = 1;
				break;
			}
			default: break;
		}
	}
}
