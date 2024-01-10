import java.io.*;

public class Mediator {

	int contractSize;
	
	public Mediator(int contractSizeA, int contractSizeB) throws FileNotFoundException{
		if(contractSizeA != contractSizeB){
			throw new FileNotFoundException("Verhandlung kann nicht durchgefuehrt werden, da Problemdaten nicht kompatibel");
		}
		this.contractSize = contractSizeA;
	}
	
	public int[] initContract(){
		int[] contract = new int[contractSize];
		for(int i=0;i<contractSize;i++)contract[i] = i;
		return contract;
	}

	public int[] constructProposal(int[] contract) {
		int[] proposal = new int[contractSize];
		for(int i=0;i<proposal.length;i++)proposal[i] = contract[i];
		int element = (int)((proposal.length-1)*Math.random());
		int wert1   = proposal[element];
		int wert2   = proposal[element+1];
		proposal[element]   = wert2;
		proposal[element+1] = wert1;
		return proposal;
	}

}
