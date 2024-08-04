describe('Login -> Menu Gestor Armazém -> Listar Entregas', () => {
  it('Login', () => {
    cy.visit('/');
    cy.url().should('includes', 'login');
    cy.get('[placeholder="Introduzir Nome de Utilizador"]').type('logistica');
    cy.get('button').click();
  });
  it('Menu Gestor Logistica', () => {
    cy.url().should('include', 'menu-gestor-logistica');
    cy.get('#listar-viagens').click();
  });
  it('Listar Entregas', () => {
    cy.url().should('include', 'listar-viagens');
  });

  it('Filtro a dados nao existentes', () => {
    cy.get('[placeholder="Ex. Mia"]').type('naoexistente');
    cy.get('table').get('td').should('not.exist');
    cy.get('table').get('td').should('not.exist');
  });

  it('Filtro a 20221205', () => {
    cy.get('[placeholder="Ex. Mia"]').clear();
    cy.get('[placeholder="Ex. Mia"]').type('20221205');
    cy.get('table').get('td').should('contains.text', '20221205');
    cy.get('table').get('td').contains('20221206').should('not.exist');
  });

});
