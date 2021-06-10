import jdk.swing.interop.SwingInterOpUtils;

public class MemoryAccess {
	static String[] dataMemory = new String[1024];
	static String[][] cacheMemory = new String[256][2];
	public static void MemAccess(String ALUresult, String readData2, String signExtend, int zeroFlag, String BranchAddressResult, int MemWrite, int MemRead, int Branch) {
		if(MemWrite == 1) {
			int newAddress = Integer.parseInt(ALUresult);
			if(cacheMemory[(int) newAddress/4][1] != null) {
				dataMemory[Integer.parseInt(cacheMemory[(int) (newAddress / 4)][1])] = cacheMemory[(int) (newAddress / 4)][0];
			}
			//Data
			cacheMemory[(int)newAddress/4][0] = readData2;
			//address
			cacheMemory[(int)newAddress/4][1] = ALUresult;
		}
		else if(MemRead == 1) {
			int readAddress = Integer.parseInt(ALUresult);
			if(cacheMemory[(int) readAddress/4][1] != null) {
				if (cacheMemory[(int) readAddress / 4][1].equals(ALUresult))
					readData2 = cacheMemory[(int) readAddress / 4][0];
			}
			else
				readData2 = dataMemory[readAddress];
		}

		if(zeroFlag == 1 && Branch == 1)
			System.out.println("NEW PC: " + BranchAddressResult);
		else 
			System.out.println("NEW PC: " + InstructionFetch.pc);

	}
}
