describe('Login -> Menu Gestor Armazém -> Criar Entrega', () => {
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
  it('Criar Armazem', () => {
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

    it('Criar Armazem', () => {
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


  it('Sucesso com todos os campos válidos', () => {
    cy.visit('/')
    cy.url().should('includes','login');
    cy.get('[placeholder="Introduzir Nome de Utilizador"]').type("armazem");
    cy.get('button').click();
    cy.url().should('include','menu-gestor-armazem');
    cy.url().should('not.include','menu-gestor-frota');
    cy.url().should('not.include','menu-gestor-logistica');
    cy.get('button[id="criar-entrega"]').click()

    cy.get('select').select('A99');
    cy.get('[placeholder="Introduzir massa"]').clear().type('200');
    cy.get('[placeholder="Introduzir tempo colocar"]').clear().type('20');
    cy.get('[placeholder="Introduzir tempo retirar"]').clear().type('17');
    cy.get('[id="criar"]').click();
    cy.on('window:alert',(t)=>{
      //assertions
      expect(t).to.contains('Entrega Criada');
    })
  })

  it('Verificar que existe na listagem de Entregas', () => {
    cy.visit('/menu-gestor-armazem');
    cy.visit('/listar-entregas');
    cy.get('table').get('td').should('contains.text', 'A99');
    cy.get('table').get('td').should('contains.text', '200');
    cy.get('table').get('td').should('contains.text', '20');
    cy.get('table').get('td').should('contains.text', '17');
  })

  it('Insucesso com o tempo retirar negativo', () => {
    cy.visit('/')
    cy.url().should('includes','login');
    cy.get('[placeholder="Introduzir Nome de Utilizador"]').type("armazem");
    cy.get('button').click();
    cy.url().should('include','menu-gestor-armazem');
    cy.url().should('not.include','menu-gestor-frota');
    cy.url().should('not.include','menu-gestor-logistica');
    cy.get('button[id="criar-entrega"]').click()

    cy.get('select').select('A99');
    cy.get('[placeholder="Introduzir massa"]').clear().type('200');
    cy.get('[placeholder="Introduzir tempo colocar"]').clear().type('-20');
    cy.get('[placeholder="Introduzir tempo retirar"]').clear().type('17');
    cy.get('[id="criar"]').click();
    cy.on('window:alert',(t)=>{
      //assertions
      expect(t).to.contains('Tempo de colocar inválido - este campo tem de ser menor que 0 e 1400.');
    })
  })

})




