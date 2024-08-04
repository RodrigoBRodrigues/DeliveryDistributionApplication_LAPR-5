import { Component, OnInit } from '@angular/core';
import * as THREE from 'three';
import { OrbitControls } from 'three/examples/jsm/controls/OrbitControls';
import TextSprite from '@seregpie/three.text-sprite';
import { Box3Helper, BoxHelper, PositionalAudio, Scene } from 'three';
import { GLTFLoader } from 'three/examples/jsm/loaders/GLTFLoader.js';
import { Posicao } from 'src/app/Model/posicao';
import { ArmazemService } from 'src/app/Services/armazem.service';
import { Armazem } from 'src/app/Model/armazem';
import { ArmazemData } from 'src/app/Model/armazemData';
import { ArmazemDataService } from 'src/app/Services/armazemData.service';
import { Title } from '@angular/platform-browser';
import { degToRad } from 'three/src/math/MathUtils';

@Component({
  selector: 'mover-camiao',
  templateUrl: './mover-camiao.component.html',
  styleUrls: ['./mover-camiao.component.css'],
})
export class MoverCamiaoComponent implements OnInit {
  armazens: Armazem[] = [];
  armazensData: ArmazemData[] = [];
  rotundas: THREE.Mesh[] = [];
  rampas: THREE.Mesh[] = [];
  roadTexture!: THREE.Texture;
  xBorder: number = 50;
  speed: number = 0.2;
  center: THREE.Vector2 = new THREE.Vector2();
  directionMax: number = Math.PI / 3.0; // 60.0 degrees
  direction: number = THREE.MathUtils.randFloatSpread(2.0 * this.directionMax); // Direction in radians
  defaultDirection: number = THREE.MathUtils.randFloatSpread(
    2.0 * this.directionMax
  );

  constructor(
    private armazemService: ArmazemService,
    private armazemDataService: ArmazemDataService,
    private titleService: Title
  ) {
    this.titleService.setTitle('Mover camiÃ£o');
  }

  ngOnInit(): void {
    const req = this.armazemService.getArmazens();
    const req2 = this.armazemDataService.getArmazemData();

    req.subscribe((x) => {
      this.armazens = x;
    });
    req2.subscribe((y) => {
      this.armazensData = y;
      this.createThreeJsBox();
    });
  }

  // Now we'll create a function to handle collisions
  handleCollision(object: THREE.Object3D) {
    // Here you can handle the collision however you want,
    // such as by changing the color of the object or displaying a message to the user
  }

  createThreeJsBox(): void {
    const canvas = document.getElementById('canvas-box');

    const scene = new THREE.Scene();

    const ambientLight = new THREE.AmbientLight(0xffffff, 0.5);
    scene.add(ambientLight);

    //adiÃ§Ã£o de nevoeiro
    scene.fog = new THREE.FogExp2(0x34583d, 0.004);

    scene.background = new THREE.CubeTextureLoader().load([
      '../../../assets/skybox/xpos.png',
      '../../../assets/skybox/xneg.png',
      '../../../assets/skybox/ypos.png',
      '../../../assets/skybox/yneg.png',
      '../../../assets/skybox/zpos.png',
      '../../../assets/skybox/zneg.png',
    ]);

    const roadTexture = new THREE.TextureLoader().load(
      '../../../assets/road/road.png'
    );
    this.roadTexture = roadTexture;

    const pointLight = new THREE.PointLight(0xffffff, 0.5);
    pointLight.position.x = 2;
    pointLight.position.y = 2;
    pointLight.position.z = 2;
    scene.add(pointLight);

    // Shared variables
    const K_LIGACAO = 1.1;
    const INFINETESIMO = 0.03;
    const K_CIRCULO = 2.1;
    let wi = 1.5;
    let RAIO = (K_CIRCULO * wi) / 2.0;
    let xP = 0;
    let yP = 0;
    let zP = 0;

    let array: Posicao[] = [];

    this.armazensData.forEach((element) => {});

    this.armazens.forEach((element) => {
      let posicao: Posicao = {
        id: element.id,
        designacao: element.designacao,
        longitude: element.longitude,
        latitude: element.latitude,
        altitude: element.altitude,
        armazensAdjacentes: [],
        url: [],
      };
      array.push(posicao);
    });

    let index = this.treatId(this.armazensData[0].armazem);
    let armazemAdjacenteIndex;
    this.armazensData.forEach((element) => {
      if (element.Id != index) {
        index = this.treatId(element.armazem);
      }
      armazemAdjacenteIndex = this.treatId(element.armazemAdjacente);
      array[Number(index) - 1].armazensAdjacentes.push(
        array[Number(armazemAdjacenteIndex) - 1]
      );
      array[Number(index) - 1].url.push(element.urlArmazem);
    });

    let positionX: number;
    let positionY: number;
    let positionZ: number;

    let positionX2: number;
    let positionY2: number;
    let positionZ2: number;

    let points, geometry, mesh;

    const normals = new Float32Array([
      0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0,
    ]);

    const indexes = [0, 2, 1, 1, 2, 3, 2, 6, 3, 3, 6, 7, 6, 4, 7, 7, 4, 5];

    array.forEach((armazem) => {
      let coordenadas = this.calculateGeographicToCarthesianCoordinates(
        armazem.longitude,
        armazem.latitude,
        armazem.altitude
      );
      positionX = coordenadas[0];
      positionY = coordenadas[2];
      positionZ = coordenadas[1];
      this.importArmazem3D(
        positionX,
        positionY,
        positionZ,
        scene,
        armazem.url[0]
      );

      /*
      let instance = new TextSprite({
        alignment: 'left',
        color: '#24ff00',
        fontFamily: '"Times New Roman", Times, serif',
        fontSize: 4,
        fontStyle: 'italic',
        text: armazem.designacao,
      });

      instance.position.x = positionX;
      instance.position.y = positionY + 5;
      instance.position.z = positionZ;

      scene.add(instance);
*/
      const geometryNode = new THREE.CircleGeometry(RAIO, 32);
      const materialNode = new THREE.MeshBasicMaterial({
        color: 0x3c6fc8,
        map: roadTexture,
      });
      const node = new THREE.Mesh(geometryNode, materialNode);
      node.rotation.x = Math.PI / 2;
      node.position.x = positionX;
      node.position.y = positionY;
      node.position.z = positionZ;
      node.material.side = THREE.DoubleSide;
      node.name = 'rotunda';
      node.castShadow = true;
      this.rotundas.push(node);

      //TODO: VER QUAL DESTES FAZ PARTE

      scene.add(node);

      armazem.armazensAdjacentes.forEach((armazensAdjacentes) => {
        let coordenadas2 = this.calculateGeographicToCarthesianCoordinates(
          armazensAdjacentes.longitude,
          armazensAdjacentes.latitude,
          armazensAdjacentes.altitude
        );
        positionX2 = coordenadas2[0];
        positionY2 = coordenadas2[2];
        positionZ2 = coordenadas2[1];
        const compOri = RAIO * 1.5;
        const compDest = RAIO * 1.5;
        const largura = 0.6;

        //busca os vertices a frente do centro
        const direction = new THREE.Vector3(
          positionX2 - positionX,
          0,
          positionZ2 - positionZ
        ).clampLength(0, 1);

        //busca os vertices ao lado do centro(armazÃƒÆ’Ã‚Â©m)
        const crossDirection = new THREE.Vector3(
          direction.x,
          0,
          direction.z
        ).cross(new THREE.Vector3(0, 1, 0));

        points = [
          new THREE.Vector3(
            positionX + crossDirection.x * largura,
            positionY,
            positionZ + crossDirection.z * largura
          ),
          new THREE.Vector3(
            positionX - crossDirection.x * largura,
            positionY,
            positionZ - crossDirection.z * largura
          ),
        ];

        points.push(
          new THREE.Vector3(
            points[0].x + direction.x * compOri,
            points[0].y,
            points[0].z + direction.z * compOri
          ),
          new THREE.Vector3(
            points[1].x + direction.x * compOri,
            points[1].y,
            points[1].z + direction.z * compOri
          )
        );

        points.push(
          new THREE.Vector3(
            positionX2 + crossDirection.x * largura,
            positionY2,
            positionZ2 + crossDirection.z * largura
          ),
          new THREE.Vector3(
            positionX2 - crossDirection.x * largura,
            positionY2,
            positionZ2 - crossDirection.z * largura
          )
        );

        points.push(
          new THREE.Vector3(
            points[4].x - direction.x * compDest,
            points[4].y,
            points[4].z - direction.z * compDest
          ),
          new THREE.Vector3(
            points[5].x - direction.x * compDest,
            points[5].y,
            points[5].z - direction.z * compDest
          )
        );

        //juntar pontos
        geometry = new THREE.BufferGeometry().setFromPoints(points);
        geometry.setAttribute('normal', new THREE.BufferAttribute(normals, 3));
        geometry.setIndex(indexes);

        let material = new THREE.MeshBasicMaterial({
          color: 0xc45b11,
          side: THREE.DoubleSide,
          map: roadTexture,
        });

        mesh = new THREE.Mesh(geometry, material);
        this.rampas.push(node);
        mesh.castShadow = true;

        mesh.name = 'rampa';
        scene.add(mesh);
      });
    });

    // DADOS CAMIAO

    var camiaoCube = new THREE.Object3D();
    let coordenadas = this.calculateGeographicToCarthesianCoordinates(
      array[4].longitude,
      array[4].latitude,
      array[4].altitude
    );
    xP = coordenadas[0];
    yP = coordenadas[2];
    zP = coordenadas[1];

    const camiaoScale = 0.0065;
    const loader = new GLTFLoader();
    loader.load(
      'http://localhost:4200/assets/truck/scene.gltf',
      function (gltf) {
        camiaoCube = gltf.scene.children[0];
        camiaoCube.scale.set(camiaoScale, camiaoScale, camiaoScale);
        camiaoCube.rotation.z = Math.PI / 2;
        camiaoCube.position.x = coordenadas[0];
        camiaoCube.position.y = coordenadas[2];
        camiaoCube.position.z = coordenadas[1];
        camiaoCube.receiveShadow = true;
        camiaoCube.castShadow = true;
        scene.add(camiaoCube);
      }
    );
    let flagMovement = false;

    //movimentaÃ§Ã£o do camiao
    let movementSide = 'f';
    document.onkeydown = function (e) {
      switch (e.key) {
        case 'ArrowUp':
          flagMovement = true;
          movementSide = 'f';
          break;
        case 'ArrowRight':
          flagMovement = true;
          movementSide = 'r';
          break;
        case 'ArrowDown':
          flagMovement = true;
          movementSide = 'b';
          break;
        case 'ArrowLeft':
          flagMovement = true;
          movementSide = 'l';
          break;
        default:
          flagMovement = false;
          break;
      }
    };

    const canvasSizes = {
      width: window.innerWidth,
      height: window.innerHeight,
    };

    const positionMatosinhos = this.calculateGeographicToCarthesianCoordinates(
      array[4].longitude,
      array[4].latitude,
      array[4].altitude
    );
    const positionXCamera = positionMatosinhos[0];
    const positionYCamera = positionMatosinhos[1];
    const positionZCamera = positionMatosinhos[2];

    const camera = new THREE.PerspectiveCamera(
      75,
      canvasSizes.width / canvasSizes.height,
      0.001,
      1000
    );

    //Change default Axis Y to be Z.

    camera.position.x = camiaoCube.position.x;
    camera.position.y = camiaoCube.position.y;
    camera.position.z = camiaoCube.position.z;
    camera.position.set(0, 45, 0);
    scene.add(camera);

    if (!canvas) {
      return;
    }

    const renderer = new THREE.WebGLRenderer({
      canvas: canvas,
      antialias: true,
    });
    renderer.setSize(canvasSizes.width, canvasSizes.height);
    renderer.shadowMap.enabled = true;

    window.addEventListener('resize', () => {
      canvasSizes.width = window.innerWidth;
      canvasSizes.height = window.innerHeight;

      camera.aspect = canvasSizes.width / canvasSizes.height;
      camera.updateProjectionMatrix();

      renderer.setSize(canvasSizes.width, canvasSizes.height);
      renderer.render(scene, camera);
    });

    window.onresize = function () {
      camera.aspect = window.innerWidth / window.innerHeight;
      camera.updateProjectionMatrix();

      renderer.setSize(window.innerWidth, window.innerHeight);
    };

    //CAMERA CONTROLS

    const controls = new OrbitControls(camera, renderer.domElement);
    controls.target.set(positionXCamera, positionZCamera, positionYCamera);
    controls.update();
    controls.enablePan = true;
    controls.autoRotate = false;
    controls.enableZoom = true;

    const clock = new THREE.Clock();

    const animateGeometry = () => {
      const elapsedTime = clock.getElapsedTime();
      const coveredDistance = this.speed;
      const directionRad = THREE.MathUtils.degToRad(this.direction);

      this.whereIsTheTruck(camiaoCube, scene);

      camera.lookAt(
        camiaoCube.position.x,
        camiaoCube.position.y,
        camiaoCube.position.z
      );
      xP = camiaoCube.position.x + this.center.x;
      yP = camiaoCube.position.y + this.center.y;
      if (flagMovement) {
        switch (movementSide) {
          case 'f':
            camiaoCube.position.add(
              new THREE.Vector3(
                coveredDistance * Math.sin(directionRad),
                0.0,
                coveredDistance * Math.cos(directionRad)
              )
            );
            //TODO: ESTA CENA DO COS ESTÃ A DAR VALORES MINUSCULOS QUANDO SE RODA, MAS ESTÃ CERTO
            break;
          case 'r':
            this.direction -= this.speed + 11.258;
            camiaoCube.rotation.z -= this.speed;
            break;
          case 'b':
            camiaoCube.position.sub(
              new THREE.Vector3(
                coveredDistance * Math.sin(directionRad),
                0.0,
                coveredDistance * Math.cos(directionRad)
              )
            );
            break;
          case 'l':
            this.direction += this.speed + 11.258;
            camiaoCube.rotation.z += this.speed;
            break;
        }
      }
      flagMovement = false;

      // Render
      renderer.render(scene, camera);

      // Call tick again on the next frame
      window.requestAnimationFrame(animateGeometry);
    };

    animateGeometry();
  }

  calculateGeographicToCarthesianCoordinates(
    xGeographic: number,
    yGeographic: number,
    zGeographic: number
  ): number[] {
    let values: number[];
    let x = ((50 - -50) / (8.7609 - 8.2451)) * (xGeographic - 8.2451) + -50;
    let y = ((50 - -50) / (41.3804 - 40.8387)) * (yGeographic - 40.8387) + -50;
    let z = ((50 - 0) / (800 - 0)) * (zGeographic - 0) + 0;
    values = [x, y, z];
    return values;
  }

  importArmazem3D(x: number, y: number, z: number, scene: Scene, url: string) {
    const baseSize = 6;
    const baseGeometry = new THREE.PlaneGeometry(baseSize, baseSize);
    const baseMaterial = new THREE.MeshBasicMaterial({ color: 0x3c6fc8 });
    const base = new THREE.Mesh(baseGeometry, baseMaterial);
    base.name = 'baseArmazem';

    const xRotation = Math.PI / 2;

    const baseZ_Offset = 6;
    base.rotation.x = xRotation;
    base.position.x = x;
    base.position.y = y;
    base.position.z = z - baseZ_Offset;
    base.material.side = THREE.DoubleSide;
    base.material.map = this.roadTexture;
    scene.add(base);

    const baseLigationSizeX = 1;
    const baseLigationSizeY = 3;
    const baseLigationGeometry = new THREE.PlaneGeometry(
      baseLigationSizeX,
      baseLigationSizeY
    );
    const baseLigationMaterial = new THREE.MeshBasicMaterial({
      color: 0x3c6fc8,
    });
    const ligation = new THREE.Mesh(baseLigationGeometry, baseLigationMaterial);
    ligation.name = 'ligacao';
    ligation.castShadow = true;
    const baseLigationZ_Offset = 3;
    ligation.rotation.x = xRotation;
    ligation.position.x = x;
    ligation.position.y = y;
    ligation.position.z = z - baseLigationZ_Offset;
    ligation.material.side = THREE.DoubleSide;
    ligation.material.map = this.roadTexture;
    scene.add(ligation);

    const armazemScale = 0.0004;
    const rootLigationZ_Offset = 6;
    var tamanhoUrl = url?.length;
    if (tamanhoUrl > 0) {
      const loader = new GLTFLoader();
      loader.load(
        url,
        function (gltf) {
          const root = gltf.scene;
          root.scale.set(armazemScale, armazemScale, armazemScale);
          root.position.x = x;
          root.position.y = y;
          root.position.z = z - rootLigationZ_Offset;
          root.castShadow = true;
          scene.add(root);
        },
        undefined,
        function (error) {
          console.log(error);
        }
      );
    }
  }

  treatId(id: string) {
    let characters = id.split('');
    let index;
    if (characters[0] != '0') {
      index = characters[1] + '' + characters[2];
    } else {
      index = characters[2];
    }
    return index;
  }
  subir = false;
  subiu = 0;
  adaptou = false;
  lastHeigth: number = 0;

  rampaIntersected = false;
  whereIsTheTruck(truck: THREE.Object3D, scene: Scene) {
    const pointer = new THREE.Vector3(
      truck.position.x,
      truck.position.y,
      truck.position.z
    );

    const direction = new THREE.Vector3(
      truck.position.x,
      truck.position.y - 999999,
      truck.position.z
    );
    const raycaster = new THREE.Raycaster(pointer);

    const raycasterDirection = new THREE.Raycaster(pointer, direction);

    const intersects = raycaster.intersectObjects(scene.children, false);
    const intersectsDirection = raycasterDirection.intersectObjects(
      scene.children,
      false
    );

    const material = new THREE.LineBasicMaterial({
      color: 0x0000ff,
    });

    const points = [];
    points.push(pointer);
    points.push();

    const geometry = new THREE.BufferGeometry().setFromPoints(points);

    const line = new THREE.Line(geometry, material);
    scene.add(line);

    if (
      intersects.length > 0 &&
      intersects[0].object.name != null &&
      intersects[0].object.name != undefined
    ) {
      if (intersects[0].object.name === 'rampa') {
        this.rampaIntersected = true;
      }
      console.log(intersects[0].distance);
      if (intersects[0].distance > 0) {
        truck.position.y += 0.3;
        this.subir = true;
      }
      if (intersects[0].distance < 0) {
        truck.position.y -= 0.3;
      }
      console.log(intersects[0].object.name);

      // if (intersects[0].distance <= 7.105427357601002e-03) {
      // console.log('estou aqui');
      // truck.position.y += 7.105427357601002e-4;
      // }
    }
    console.log(this.subir, ' ', intersects.length);
    if (
      (this.subir == false && this.subiu > 0) ||
      (this.subir && intersects.length == 0)
    ) {
      console.log('Adaptei');
      truck.position.y -= 0.3;
      this.adaptou = true;
    }
    if (this.subir && intersects.length == 0) {
      this.lastHeigth = truck.position.y;
    }

    if (this.subir && intersects.length > 0 && this.adaptou) {
      this.subir = false;
      this.subiu++;
      console.log('HAHAHAHAHAHANHAAH', this.subiu);
      this.adaptou = false;
    }
  }
}
