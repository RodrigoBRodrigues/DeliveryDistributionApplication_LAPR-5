# US2004
=======================================


# 1. Requisitos

**US2004** As Warehouse Employee, I want to access the list of orders that have already been prepared by the AGVs and be able to update any of those orders as having been dispatched for customer delivery.

A interpretação feita deste requisito foi no sentido de devolver ao end user, uma lista de todas as instâncias de ProductOrder's cujo estado seja "READY_FOR_CARRIER"; consequentemente, deverá haver opção de atualiza-las para "DISPATCHED".

# 2. Análise

## 2.1 Futuras implementações para os outros atores

* Este processo será feito pelo Warehouse Employee, um dos atores que podem fazer esta ação. Por este motivo esta ação não será partilhada por todos. *

## 2.2 Sequência das ações

* O warehouse employee irá iniciar o processo de visualização de lista de encomendas preparadas, de seguida poderá atualizar uma dessas encomendas para o estado "DISPATCHED" *

## 2.3 Regras de negócio associadas aos atributos de um ProductOrder

* OrderState: Correspode ao estado em que se encontra uma order, no que se refere à sua entrega. Por defeito, todas as orders deverão ser criadas no estado "REGISTERED"

## 2.4 Pré Condições

* Existirem ProductOrders no sistema no estado "READY_FOR_PACKAGING".

## 2.5 Pós Condições

A informação das ProductOrders é persistida.

## 2.6 SSD

![SSD-Diagram](US2004_SSD.svg/)

# 3. Design

## 3.1. Realização da Funcionalidade

![SD-Diagram](US2004_SD.svg/)

## 3.2. Diagrama de Classes

![CD-Diagram](US2004_CD.svg/)

## 3.3. Padrões Aplicados

* Foi utilizado o CRUD (Create, Read, Update, Delete) para trabalhar sobre os ProductOrders.

* Foi utilizado o GRASP:

* Foi utilizado o Builder. O padrão builder dá-nos um processo passo a passo
para construir um objeto completo. Este processo tem sempre a mesma implementação, porém os objetos finais podem possuir
diferentes representações. Neste contexto o processo irá passar por criar os atributos obrigatórios de construtor, dando
a possibilidade de definir apenas alguns atributos opcionais. Exemplo: AGV com AGVDock, mas sem AGVState.

* Foram utilizados o padrão repository, de modo a isolar os objetos de domínio de lógica de bases de dados. Os nossos objetos
de domínio, que por já são complexos contendo muitas regras de domínio para impor, beneficia de outra camada onde apenas
teremos lógica de bases de dados. Isto ajuda-nos a reduzir código duplicado, fazendo com que a layer de repositório
possua capacidades de fazer querying complexo. Um repositório encapsula a lista de objetos persistidos numa base de dados
dando-nos uma visão orientada a objetos à camada de persitência.


## 3.4. Testes
*Nesta secção deve sistematizar como os testes foram concebidos para permitir uma correta aferição da satisfação dos requisitos.*

**Teste 1:** verificar que as ProductOrder são atualizadas na base de dados

# 4. Implementação

## UpdateOrderStateReadyUI

@Override
	protected boolean doShow() {

			Iterable<ProductOrder> lProdOrder = ctrl.getListProductOrders();
			int i = 1;

			System.out.print("Order List: \n" +
							"--------------------------------------------------------------\n");
			for (ProductOrder prod : lProdOrder ) {
					if(prod.getOrderState().toString().equals(OrderState.READY_FOR_CARRIER.toString())){
							System.out.println(i + " - \n" + prod.shortToString());
							i++;
					}
			}
			System.out.print("\n" +
							"0 - to cancel operation");
			System.out.print("--------------------------------------------------------------\n");
			ProductOrder rProductOrder = (ProductOrder) Utils.selectsObject((List) lProdOrder);

			if (rProductOrder.equals(null)){
					System.out.printf("No Product Order selected! (Null Product Order");
					return false;
			}

			System.out.println();

			String confirmation = null;
			do {
					confirmation= Utils.readLineFromConsole("Do you wish to update ProductOrder#" + rProductOrder.identity() + "'s state to DISPATCHED?(Y/N)\"");
					if(confirmation.equalsIgnoreCase("y")) {
							ProductOrder nProductOrder = rProductOrder;

							if(this.ctrl.changeOrderState(nProductOrder)){
									this.ctrl.save(nProductOrder,rProductOrder);
									System.out.print("--------------------------------------------------------------\n" +
													"Operation successful!" + "\n" +
													"--------------------------------------------------------------\n" +
													"Here is the list of DISPATCHED Product Orders:" + "\n" +
													this.ctrl.printDispatchedProductOrders() + "\n" +
													"--------------------------------------------------------------\n");
									break;
							} else {
									System.out.print("--------------------------------------------------------------\n" +
													"Operation unsuccessful!" + "\n" +
													"--------------------------------------------------------------\n");
									break;
							}

					} else if (confirmation.equalsIgnoreCase("n")) {
							System.out.print("Operation successfully canceled!\n");
							break;
					} else {
							System.out.println("Enter Y to confirm, or N to cancel the order!");
					}

			}while(!confirmation.equalsIgnoreCase("y") || !confirmation.equalsIgnoreCase("n"));


			return false;
	}


	@Override
	public String headline() {
			return "Update Product Order to DISPATCHED";
	}
	}


# 5. Integração/Demonstração

- Foi adicionada a opção (Warehouse Management -> Update Product Order to DISPATCHED) as menu do Warehouse Employee
- Deve ser escolhida uma ProductOrder.

# 6. Observações
