package eapli.base.infrastructure.bootstrapers.demo;


import eapli.base.clientusermanagement.domain.*;
import eapli.base.usermanagement.application.AddCustomerController;
import eapli.framework.actions.Action;

import java.util.ArrayList;
import java.util.List;

public class CustomerBootstraper implements Action {
    AddCustomerController customerController = new AddCustomerController();

    @Override
    public boolean execute() {
        Vat vat1 = Vat.valueOf("176232812");
        List<Address> addressList1 = new ArrayList<>();
        addressList1.add(new Address("Rua dos Reis", "75", "3880-241", "Ovar", "Portugal", AddressType.SHIPMENT));
        customerController.customerBuilder("Jorge", "Sousa", vat1.vatId(), "jorgesousa@gmail.com",
                "351938276432"
                , "2000-02-02", Gender.MALE, addressList1, "customer1","Customer1");

        Vat vat2 = Vat.valueOf("111122223");
        List<Address> addressList2 = new ArrayList<>();
        addressList2.add(new Address("Rua da Avenida", "35", "4910-575", "Lisboa", "Portugal", AddressType.SHIPMENT));
        customerController.customerBuilder("Joao", "Gabriel", vat2.vatId(), "joaogabriel@gmail.com",
                "351965630045"
                , "2001-12-23", Gender.MALE, addressList2, "customer2","Customer2");

        Vat vat3 = Vat.valueOf("99821222");
        List<Address> addressList3 = new ArrayList<>();
        addressList3.add(new Address("Rua do Cafe", "3", "1229-241", "Porto", "Portugal", AddressType.SHIPMENT));
        customerController.customerBuilder("Mario", "Soares", vat3.vatId(), "mariosoares@gmail.com",
                "351938276432"
                , "1998-01-12", Gender.MALE, addressList3, "customer3","Customer3");



        return false;
    }
}
