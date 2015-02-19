package name.abhijitsarkar.webservices.jaxws.addressing.client;

import name.abhijitsarkar.webservices.jaxws.addressing.client.generated.CalculatorAddrService;

public class AddressingClient {
	public static void main(String[] args) {
		System.out.printf("The sum of %d and %d is: %d.", 1, 2,
				new AddressingClient().add(1, 2));
	}

	public int add(int augend, int addend) {
		return new CalculatorAddrService().getCalculatorAddrPort().add(augend,
				addend);
	}
}
