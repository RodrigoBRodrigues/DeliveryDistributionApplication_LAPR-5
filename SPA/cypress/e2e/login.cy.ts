describe('Login', () => {
    it('Nao consegue entrar se o formulário for inválido', () => {
      cy.visit('/')
      cy.url().should('includes','login');
      cy.get('[placeholder="Introduzir Nome de Utilizador"]').type("jorgeFerreira");
      cy.get('[placeholder="Introduzir Palavra-Passe"]').type("Passs");
      cy.get('button').click();
      cy.url().should('not.include','menu-gestor-armazem');
      cy.url().should('not.include','menu-gestor-frota');
      cy.url().should('not.include','menu-gestor-logistica');
    })
  })

describe('SSO', () => {
    it('Nao consegue entrar se o formulário for inválido', () => {
      cy.visit('/')
      cy.url().should('includes','login');
      cy.get('[id=buttonDiv').click();
      cy.wait(3000);
    })
  })
