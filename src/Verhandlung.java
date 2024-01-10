import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;


//SIMULATION!

/*
 * Was ist das "Problem" der nachfolgenden Verhandlung?
 * - Fr�he Stagnation, da die Agenten fr�hzeitig neue Contracte ablehnen
 * - Verhandlung ist nur f�r wenige Agenten geeignet, da mit Anzahl Agenten auch die Stagnationsgefahr w�chst
 * 
 * Aufgabe: Entwicklung und Anaylse einer effizienteren Verhandlung. Eine Verhandlung ist effizienter, wenn
 * eine fr�he Stagnation vermieden wird und eine sozial-effiziente Gesamtl�sung gefunden werden kann.
 * 
 * Ideen:
 * - Agenten m�ssen auch Verschlechterungen akzeptieren bzw. zustimmen, die einzuhaltende Mindestakzeptanzrate wird vom Mediator vorgegeben
 * - Agenten schlagen alternative Kontrakte vor
 * - Agenten konstruieren gemeinsam einen Kontrakt
 * - In jeder Runde werden mehrere alternative Kontrakte vorgeschlagen
 * - Alternative Konstruktionsmechanismen f�r Kontrakte
 * - Ausgleichszahlungen der Agenten (nur m�glich, wenn beide Agenten eine monetaere Zielfunktion haben
 * 
 */


public class Verhandlung {	

		public static void main(String[] args) {
			int[] contract, proposal, alternativeProposal;
			Agent agA, agB;
			Mediator med;
			int maxRounds, round;
			boolean voteA, voteB;
			Random ran = new Random();
			double mindestakzeptanzrateA = 0.3;
			double mindestakzeptanzrateB = 0.3;
			
			try{
				agA = new SupplierAgent(new File("data/daten3ASupplier_200.txt"));
				agB = new CustomerAgent(new File("data/daten4BCustomer_200_5.txt"));
				med = new Mediator(agA.getContractSize(), agB.getContractSize());
				
				//Verhandlung initialisieren
				contract  = med.initContract();							//Vertrag=L�sung=Jobliste
				maxRounds = 100000;										//Verhandlungsrunden
				ausgabe(agA, agB, 0, contract, mindestakzeptanzrateA, mindestakzeptanzrateB);
				
				//Verhandlung starten	
				for(round=1;round<maxRounds;round++) {					//Mediator				
					proposal = med.constructProposal(contract);		//zweck: Win-win
					alternativeProposal = med.constructProposal(contract);
					voteA    = agA.vote(contract, proposal);            //Autonomie + Private Infos
					voteB    = agB.vote(contract, proposal);

					if(voteA && voteB) {
						contract = proposal;
						ausgabe(agA, agB, round, contract, mindestakzeptanzrateA, mindestakzeptanzrateB);
					}else if(voteA ||voteB) {
						if(!voteA) {
							if(ran.nextDouble() < mindestakzeptanzrateA) {
								mindestakzeptanzrateA -= 0.001;
								voteA = true;
							}
						}
						if(!voteB) {
							if(ran.nextDouble() < mindestakzeptanzrateB) {
								mindestakzeptanzrateB -= 0.001;
								voteB = true;
							}
						}
						if(voteA && voteB) {
							contract = proposal;
							ausgabe(agA, agB, round, contract, mindestakzeptanzrateA, mindestakzeptanzrateB);
						}
					}else {
						voteA    = agA.vote(contract, alternativeProposal);            //Autonomie + Private Infos
						voteB    = agB.vote(contract, alternativeProposal);
						
						if(voteA && voteB) {
							contract = alternativeProposal;
							ausgabe(agA, agB, round, contract, mindestakzeptanzrateA, mindestakzeptanzrateB);
						}
					}
				}			
				
			}
			catch(FileNotFoundException e){
				System.out.println(e.getMessage());
			}
		}
		
		public static void ausgabe(Agent a1, Agent a2, int i, int[] contract, double minA, double minB){
			System.out.print(i + " -> " );
			a1.printUtility(contract);
			System.out.print("  ");
			a2.printUtility(contract);
			System.out.printf("   minR:   A:%5.4f      B:%5.4f%n", minA, minB);
		}

}