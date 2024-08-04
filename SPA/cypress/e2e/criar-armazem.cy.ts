describe('Login -> Menu Gestor Armazém -> Criar Armazém', () => {
    it('Login', () => {
      cy.visit('/')
      cy.url().should('includes','login');
      cy.get('[placeholder="Introduzir Nome de Utilizador"]').type("armazem");
      cy.get('button').click();
    })
    it('Menu Gestor Armazém', () => {
        cy.url().should('include','menu-gestor-armazem');
        cy.get('#criar-armazem').click();
    })
    it('Criar Armazém', () => {
        cy.url().should('include','criar-armazem');
    })
})

describe('Criar Armazém válido e verificar que ele está presente na Listagem', () => {
    it('Login', () => {
        cy.visit('/')
        cy.url().should('includes','login');
        cy.get('[placeholder="Introduzir Nome de Utilizador"]').type("armazem");
        cy.get('button').click();
      })

      it('Menu Gestor Armazém', () => {
          cy.url().should('include','menu-gestor-armazem');
          cy.get('#criar-armazem').click();
      })

      it('Criar Armazém', () => {
          cy.url().should('include','criar-armazem');
      })

      it('Inserir dados válidos', () => {
        cy.viewport(1920, 1080);
        cy.get('[placeholder="Introduzir Identificador do Armazém"').type('A99');
        cy.get('[placeholder="Introduzir Designação"]').type('Carvalhos');
        cy.get('[placeholder="Introduzir Rua"]').type('Loja Central');
        cy.get('[placeholder="Introduzir Código Postal"]').type('3350-852');
        cy.get('[placeholder="Introduzir Latitude"]').type('20');
        cy.get('[placeholder="Introduzir Longitude"]').type('30');
        cy.get('[placeholder="Introduzir Altitude"]').type('5');
        cy.get('button').click();
        cy.on('window:alert',(t)=>{
            expect(t).to.contains('Armazém Criado!');
         })
         cy.url().should('include','criar-armazem');
    })

    it('Verificar que existe na listagem de Armazéns', () => {
        cy.visit('/menu-gestor-armazem');
        cy.visit('/listar-armazens');
        cy.get('table').get('td').should('contains.text', 'A99');
        cy.get('table').get('td').should('contains.text', 'Carvalhos');
        cy.get('table').get('td').should('contains.text', 'Loja Central');
        cy.get('table').get('td').should('contains.text', '3350-852');
        cy.get('table').get('td').should('contains.text', '20');
        cy.get('table').get('td').should('contains.text', '30');
        cy.get('table').get('td').should('contains.text', '5');
    })

})

describe('Criar Armazém com Id inválido e verificar que não está presente na Listagem', () => {
    it('Login', () => {
        cy.visit('/')
        cy.url().should('includes','login');
        cy.get('[placeholder="Introduzir Nome de Utilizador"]').type("armazem");
        cy.get('button').click();
      })

      it('Menu Gestor Armazém', () => {
          cy.url().should('include','menu-gestor-armazem');
          cy.get('#criar-armazem').click();
      })

      it('Criar Armazém', () => {
          cy.url().should('include','criar-armazem');
      })

      it('Inserir dados válidos', () => {
        cy.viewport(1920, 1080);
        cy.get('[placeholder="Introduzir Identificador do Armazém"').type('Z');
        cy.get('[placeholder="Introduzir Designação"]').type('Caldas');
        cy.get('[placeholder="Introduzir Rua"]').type('Continente');
        cy.get('[placeholder="Introduzir Código Postal"]').type('33502852');
        cy.get('[placeholder="Introduzir Latitude"]').type('20');
        cy.get('[placeholder="Introduzir Longitude"]').type('50');
        cy.get('[placeholder="Introduzir Altitude"]').type('5');
        cy.get('button').click();
        cy.url().should('include','criar-armazem');
    })

    it('Verificar que existe na listagem de Armazéns', () => {
        cy.visit('/menu-gestor-armazem');
        cy.visit('/listar-armazens');
        cy.get('table').get('td').should('not.contains.text', 'Z');
    })

})

describe('Criar Armazém com Código Postal inválido e verificar que não está presente na Listagem', () => {
    it('Login', () => {
        cy.visit('/')
        cy.url().should('includes','login');
        cy.get('[placeholder="Introduzir Nome de Utilizador"]').type("armazem");
        cy.get('button').click();
      })

      it('Menu Gestor Armazém', () => {
          cy.url().should('include','menu-gestor-armazem');
          cy.get('#criar-armazem').click();
      })

      it('Criar Armazém', () => {
          cy.url().should('include','criar-armazem');
      })

      it('Inserir dados válidos', () => {
        cy.viewport(1920, 1080);
        cy.get('[placeholder="Introduzir Identificador do Armazém"').type('AZ1');
        cy.get('[placeholder="Introduzir Designação"]').type('Caldas');
        cy.get('[placeholder="Introduzir Rua"]').type('Continente');
        cy.get('[placeholder="Introduzir Código Postal"]').type('33502852');
        cy.get('[placeholder="Introduzir Latitude"]').type('20');
        cy.get('[placeholder="Introduzir Longitude"]').type('50');
        cy.get('[placeholder="Introduzir Altitude"]').type('5');
        cy.get('button').click();
        cy.url().should('include','criar-armazem');
    })

    it('Verificar que existe na listagem de Armazéns', () => {
        cy.visit('/menu-gestor-armazem');
        cy.visit('/listar-armazens');
        cy.get('table').get('td').should('not.contains.text', 'AZ1');
        cy.get('table').get('td').should('not.contains.text', '33502852');
    })

})

describe('Criar Armazém com Localização inválida e verificar que não está presente na Listagem', () => {
    it('Login', () => {
        cy.visit('/')
        cy.url().should('includes','login');
        cy.get('[placeholder="Introduzir Nome de Utilizador"]').type("armazem");
        cy.get('button').click();
      })

      it('Menu Gestor Armazém', () => {
          cy.url().should('include','menu-gestor-armazem');
          cy.get('#criar-armazem').click();
      })

      it('Criar Armazém', () => {
          cy.url().should('include','criar-armazem');
      })

      it('Inserir dados válidos', () => {
        cy.viewport(1920, 1080);
        cy.get('[placeholder="Introduzir Identificador do Armazém"').type('AZ1');
        cy.get('[placeholder="Introduzir Designação"]').type('Caldas');
        cy.get('[placeholder="Introduzir Rua"]').type('Continente');
        cy.get('[placeholder="Introduzir Código Postal"]').type('7410-852');
        cy.get('[placeholder="Introduzir Latitude"]').type('250');
        cy.get('[placeholder="Introduzir Longitude"]').type('250');
        cy.get('[placeholder="Introduzir Altitude"]').type('250');
        cy.get('button').click();
        cy.url().should('include','criar-armazem');
    })

    it('Verificar que existe na listagem de Armazéns', () => {
        cy.visit('/menu-gestor-armazem');
        cy.visit('/listar-armazens');
        cy.get('table').get('td').should('not.contains.text', 'AZ1');
    })

})
  
  