import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CustomerAgent extends Agent {

	private int[][] timeMatrix;

	public CustomerAgent(File file) throws FileNotFoundException {

		Scanner scanner = new Scanner(file);
		int jobs = scanner.nextInt();
		int machines = scanner.nextInt();
		timeMatrix = new int[jobs][machines];
		for (int i = 0; i < timeMatrix.length; i++) {
			for (int j = 0; j < timeMatrix[i].length; j++) {
				int x = scanner.nextInt();
				timeMatrix[i][j] = x;
			}
		}

		scanner.close();

	}

	public boolean vote(int[] contract, int[] proposal) {
		int timeContract = evaluateNEW(contract);
		int timeProposal = evaluateNEW(proposal);
		if (timeProposal < timeContract)
			return true;
		else
			return false;
	}

	
	
	public int getContractSize() {
		return timeMatrix.length;
	}

	public void printUtility(int[] contract) {
		System.out.print(evaluateNEW(contract));
	}
	
	private int evaluateNEW(int[] solution) {
		int anzM = timeMatrix[0].length;
		
		if(timeMatrix.length != solution.length)System.out.println("Fehler in ");
		int[][] start = new int[timeMatrix.length][timeMatrix[0].length];

		for(int i=0;i<start.length;i++) {
			for(int j=0;j<start[i].length;j++) {
				start[i][j] = 0;
			}
		}
		
		int job = solution[0];
		for(int m=1;m<anzM;m++) {
			start[job][m] = start[job][m-1] + timeMatrix[job][m-1];
		}
		
		for(int j=1;j<solution.length;j++) {
			int delay             = 0;
			int vorg              = solution[j-1];
			job                   = solution[j];
			boolean delayErhoehen;  
			do {
				delayErhoehen = false;
				start[job][0] = start[vorg][0] + timeMatrix[vorg][0] + delay;
				for(int m=1;m<anzM;m++) {					
					start[job][m] = start[job][m-1] + timeMatrix[job][m-1];
					if(start[job][m] < start[vorg][m]+timeMatrix[vorg][m]) {
						delayErhoehen = true;
						delay++;
						break;
					}
				}
			}while(delayErhoehen);
		}
		int last = solution[solution.length-1];
		
		
//		for(int j=0;j<solution.length;j++) {
//			for(int m=0;m<anzM;m++) {
//				System.out.print(start[j][m] + "\t");
//			}
//			System.out.println();
//		}
		
		return (start[last][anzM-1]+timeMatrix[last][anzM-1]);
	}

	
}
