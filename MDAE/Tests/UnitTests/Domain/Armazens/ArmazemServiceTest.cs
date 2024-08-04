using System.Collections.Generic;
using DDDSample1.Domain.Armazens;
using DDDSample1.Domain.Shared;
using System;
using NUnit.Framework;
using Moq;



namespace Tests.Domain.Armazens{
    public class ArmazemServiceTest {

        private ArmazemId id; private string armazemId; 
        private Designacao designacao; private string nome;
        private Endereco endereco; private  string rua; private string codigoPostal;
        private Localizacao localizacao; private double latitude; private double longitude; private double altitude;
        private Armazem armazem;
        private readonly Mock<IUnitOfWork> unitOfWorkMock = new Mock<IUnitOfWork>();
        private readonly Mock<IArmazemRepository> repositoryMock = new Mock<IArmazemRepository>();
        private ArmazemService service;
        

        [SetUp]
        public void SetUp() {
            armazemId = "X77";
            id = new ArmazemId(armazemId);
            nome = "Anadia";
            designacao = new Designacao(nome);
            rua = "Santo Amaro"; codigoPostal = "3780-777";
            endereco = new Endereco(rua,codigoPostal);
            latitude = 2.0; longitude = 2.0; altitude = 200;
            localizacao = new Localizacao(latitude, longitude, altitude);

            armazem = new Armazem (armazemId, nome, rua, codigoPostal, latitude, longitude, altitude, true);

            service = new ArmazemService(unitOfWorkMock.Object,repositoryMock.Object);

            this.repositoryMock.Setup(t => t.AddAsync(It.IsAny<Armazem>()));
            this.repositoryMock.Setup(t => t.Add(It.IsAny<Armazem>()));
            this.repositoryMock.Setup(t => t.GetByIdAsync(It.IsAny<ArmazemId>()));
            this.repositoryMock.Setup(t => t.GetByDesignacao(It.IsAny<Designacao>()));
            this.repositoryMock.Setup(t => t.GetAllAsync());
        }


        [Test]
        public void CreateArmazem () {

            ArmazemDto dto = ArmazemMapper.toArmazemDto(armazem);

            var result1 = this.service.AddAsync(dto);
            this.repositoryMock.Verify(t => t.AddAsync(It.IsAny<Armazem>()), Times.AtLeastOnce());

            var result2 = this.service.GetByIdAsync(id);
            this.repositoryMock.Verify(t => t.GetByIdAsync(It.IsAny<ArmazemId>()), Times.AtLeastOnce());
            
            Assert.AreEqual(result1.ToString(), result2.ToString());    
        }

         [Test]
        public void UpdateArmazem () {
            ArmazemDto dto = ArmazemMapper.toArmazemDto(armazem);

            var result1 = this.service.AddAsync(dto);
            this.repositoryMock.Verify(t => t.AddAsync(It.IsAny<Armazem>()), Times.AtLeastOnce());

            var result2 = this.service.UpdateAsync(dto);
            this.repositoryMock.Verify(t => t.GetByIdAsync(It.IsAny<ArmazemId>()), Times.AtLeastOnce());

            var result3 = this.service.GetByIdAsync(id);
            this.repositoryMock.Verify(t => t.GetByIdAsync(It.IsAny<ArmazemId>()), Times.AtLeastOnce());

            Assert.AreNotEqual(result1,result2);
            Assert.AreEqual(result2,result3);  
        }

        [Test]
        public void GetByDesignacao () {
            ArmazemDto dto = ArmazemMapper.toArmazemDto(armazem);

            var result1 = this.service.AddAsync(dto);
            this.repositoryMock.Verify(t => t.AddAsync(It.IsAny<Armazem>()), Times.AtLeastOnce());

            var result2 = this.service.GetByDesignacao(nome);
            this.repositoryMock.Verify(t => t.GetByDesignacao(It.IsAny<Designacao>()), Times.AtLeastOnce());

            var result3 = this.service.GetByIdAsync(id);
            this.repositoryMock.Verify(t => t.GetByIdAsync(It.IsAny<ArmazemId>()), Times.AtLeastOnce());

            Assert.AreNotEqual(result1,result2);
            Assert.AreEqual(result2,result3);

            var result4 = this.service.GetByDesignacao("Comendador Nando");
            this.repositoryMock.Verify(t => t.GetByDesignacao(It.IsAny<Designacao>()), Times.AtLeastOnce());

            Assert.Null(result4.Result);
        }

        [Test]
        public void GetArmazens () {
            ArmazemDto dto = ArmazemMapper.toArmazemDto(armazem);

            var result1 = this.service.AddAsync(dto);
            this.repositoryMock.Verify(t => t.AddAsync(It.IsAny<Armazem>()), Times.AtLeastOnce());

            var result2 = this.service.GetAllAsync();
            this.repositoryMock.Verify(t => t.GetAllAsync(), Times.AtLeastOnce());

            Assert.AreNotEqual(result1,result2); 
        }
        

    }
    
}