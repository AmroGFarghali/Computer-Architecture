public class WriteBackClass {
	public static void WriteBack(int RegDst, String ALUresult, String readData, int MemToReg) {
		if(InstructionDecode.RegWrite == 1) {
			if(RegDst == 0) 
				RegDst = InstructionDecode.rt;
			else
				RegDst = InstructionDecode.rd;
			if(MemToReg == 0)
				InstructionDecode.registerFile[RegDst].setValue(ALUresult);
			else 
				InstructionDecode.registerFile[RegDst].setValue(readData);
			//System.out.println("Finished instruction -------------------------------------------");
		}
	}
}