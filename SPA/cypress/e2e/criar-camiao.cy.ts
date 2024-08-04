describe('Login -> Menu Gestor Frota -> Criar Camiao', () => {
    it('Login', () => {
      cy.visit('/')
      cy.url().should('includes','login');
      cy.get('[placeholder="Introduzir Nome de Utilizador"]').type("frota");
      cy.get('button').click();
    })
    it('Menu Gestor Frota', () => {
        cy.url().should('include','menu-gestor-frota');
        cy.get('#criar-camiao').click();
    })
    it('Criar Camião', () => {
        cy.url().should('include','criar-camiao');
    })
})


describe('Criar Camião válido e verificar que ele está presente na Listagem', () => {
    it('Login', () => {
      cy.visit('/')
      cy.url().should('includes','login');
      cy.get('[placeholder="Introduzir Nome de Utilizador"]').type("frota");
      cy.get('button').click();
    })
    it('Menu Gestor Frota', () => {
        cy.url().should('include','menu-gestor-frota');
        cy.get('#criar-camiao').click();
    })
    it('Criar Camião', () => {
        cy.url().should('include','criar-camiao');
    })

    it('Inserir dados válidos', () => {
        cy.viewport(1920, 1080);
        cy.get('[placeholder="Introduzir matricula"').type('AA-99-99');
        cy.get('[placeholder="Introduzir caracteristica"]').type('eTruck01');
        cy.get('[placeholder="Introduzir tara"]').type('8000');
        cy.get('[placeholder="Introduzir capacidade"]').type('3500');
        cy.get('[placeholder="Introduzir carga"]').type('4000');
        cy.get('[placeholder="Introduzir autonomia"]').type('80');
        cy.get('[placeholder="Introduzir tempo"]').type('120');
        cy.get('button').click();
        cy.on('window:alert',(t)=>{
            expect(t).to.contains('Camião Criado!');
         })
         cy.url().should('include','criar-camiao');
    })
    it('Verificar que existe na listagem de Camioes', () => {
        cy.visit('/menu-gestor-frota');
        cy.visit('/listar-camioes');
        cy.get('table').get('td').should('contains.text', 'AA-99-99');
        cy.get('table').get('td').should('contains.text', 'eTruck01');
        cy.get('table').get('td').should('contains.text', '8000');
        cy.get('table').get('td').should('contains.text', '3500');
        cy.get('table').get('td').should('contains.text', '4000');
        cy.get('table').get('td').should('contains.text', '80');
        cy.get('table').get('td').should('contains.text', '120');
    })
    
})
describe('Criar Camiao com matricula inválida e verificar que não está presente na Listagem', () => {
    it('Login', () => {
        cy.visit('/')
        cy.url().should('includes','login');
        cy.get('[placeholder="Introduzir Nome de Utilizador"]').type("frota");
        cy.get('button').click();
      })

      it('Menu Gestor Frota', () => {
          cy.url().should('include','menu-gestor-frota');
          cy.get('#criar-camiao').click();
      })

      it('Criar Camião', () => {
          cy.url().should('include','criar-camiao');
      })

      it('Inserir dados válidos', () => {
        cy.viewport(1920, 1080);
        cy.get('[placeholder="Introduzir matricula"').type('AABBCC');
        cy.get('[placeholder="Introduzir caracteristica"]').type('eTruck01');
        cy.get('[placeholder="Introduzir tara"]').type('8000');
        cy.get('[placeholder="Introduzir capacidade"]').type('3500');
        cy.get('[placeholder="Introduzir carga"]').type('4000');
        cy.get('[placeholder="Introduzir autonomia"]').type('80');
        cy.get('[placeholder="Introduzir tempo"]').type('120');
        cy.get('button').click();
        cy.on('window:alert',(t)=>{
            expect(t).to.contains('Formato da matrícula inválido.\n');
         })
        cy.url().should('include','criar-camiao');

    })

    it('Verificar que não existe na listagem de Camiões', () => {
        cy.visit('/menu-gestor-frota');
        cy.visit('/listar-camioes');
        cy.get('table').get('td').should('not.contains.text', 'AABBCC');
    })

})
