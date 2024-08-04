describe('Login -> Menu Gestor Logística -> Planeamento de Rota', () => {
    it('Login', () => {
      cy.visit('/')
      cy.url().should('includes','login');
      cy.get('[placeholder="Introduzir Nome de Utilizador"]').type("logistica");
      cy.get('button').click();
    })
    it('Menu Gestor Logística', () => {
        cy.url().should('include','menu-gestor-logistica');
        cy.get('#planeamento-rota').click();
    })
    it('Planeamento Rota', () => {
        cy.url().should('include','planeamento-rota');
        cy.get('#plan').click();
    })
})

describe('Executar Planeamento de Rota Mock', () => {
    it('Login', () => {
      cy.visit('/')
      cy.url().should('includes','login');
      cy.get('[placeholder="Introduzir Nome de Utilizador"]').type("logistica");
      cy.get('button').click();
    })
    it('Menu Gestor Logística', () => {
        cy.url().should('include','menu-gestor-logistica');
        cy.get('#planeamento-rota').click();
    })
    it('Planeamento Rota', () => {
        cy.url().should('include','planeamento-rota');
        cy.get('#plan').click();
    })
})

describe('Verificar existência de Matosinhos', () => {
    it('Login', () => {
      cy.visit('/')
      cy.url().should('includes','login');
      cy.get('[placeholder="Introduzir Nome de Utilizador"]').type("logistica");
      cy.get('button').click();
    })
    it('Menu Gestor Logística', () => {
        cy.url().should('include','menu-gestor-logistica');
        cy.get('#planeamento-rota').click();
    })
    it('Planeamento Rota', () => {
        cy.url().should('include','planeamento-rota');
        cy.get('#plan').click();
        cy.get('#rota').should('contain.text',"Matosinhos");
    })
})

