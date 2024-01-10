import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SupplierAgent extends Agent {

	private int[][] costMatrix;

	public SupplierAgent(File file) throws FileNotFoundException {

		Scanner scanner = new Scanner(file);
		int dim = scanner.nextInt();
		costMatrix = new int[dim][dim];
		for (int i = 0; i < dim; i++) {
			for (int j = 0; j < dim; j++) {
				int x = scanner.nextInt();
				costMatrix[i][j] = x;
			}
		}
		scanner.close();
	}

	public boolean vote(int[] contract, int[] proposal) {
		int costContract = evaluate(contract);
		int costProposal = evaluate(proposal);
		if (costProposal < costContract)
			return true;
		else
			return false;
	}

	public int getContractSize() {
		return costMatrix.length;
	}

	public void printUtility(int[] contract) {
		System.out.print(evaluate(contract));
	}

	
	private int evaluate(int[] contract) {

		int result = 0;
		for (int i = 0; i < contract.length - 1; i++) {
			int zeile = contract[i];
			int spalte = contract[i + 1];
			result += costMatrix[zeile][spalte];
		}

		return result;
	}

}