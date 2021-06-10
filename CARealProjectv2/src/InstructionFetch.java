public class InstructionFetch {
	
	static String InstructionMemory[] = new String[1024];
	static String pc = "00";
	static int totalInstructions = 0;
	static String instruction = "F";
	
	public static void InstFetch(String pc) {
		if(totalInstructions != 0) {
			int p = Integer.parseInt(pc,2);
			if(p >= 0 && p < 1024) {
				instruction = InstructionMemory[p/4];
				ProgCount();
			}
		} else {
			instruction = "F";
		}
	}
	
	public static void loadInstruction(String inst) {
		if(totalInstructions < 1024) {
			InstructionMemory[totalInstructions] = inst;
			totalInstructions++;
		}
	}
	
	public static void ProgCount() {
		int p = Integer.parseInt(pc,2);
		p += 4;
		pc = Integer.toBinaryString(p);
		while(pc.length()<32)
			pc = "0" + pc;
	}
}
