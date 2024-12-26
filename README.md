# RIOPK

## Система учета медицинских средств и препаратов

## Оглавление

1. [Описание проекта](#описание-проекта)
2. [Установка](#установка)
3. [Использование](#использование)
4. [Архитектура](#архитектура)
    - [Нотация C4](#нотация-c4)
    - [UML диаграммы](#UML)
    - [Схема БД](#схема_БД)
    - [Диаграмма классов](#диаграмма-классов)
    - [Дизайн](#дизайн)
5. [Документация](#документация)
    - [API](#api)
6. [Тестирование](#тестирование)

## Описание проекта

## Установка

## Использование

## Архитектура

### Нотация C4

Контейнерный уровень:
![image](https://github.com/user-attachments/assets/b4341497-3fe1-4f8c-9315-82a5a62118b6)

Компонентный уровень:
![image](https://github.com/user-attachments/assets/fc907144-a5bc-467e-be01-4d98b677ea7b)

### UML

![image](https://github.com/user-attachments/assets/fb30a853-1d94-4288-96f7-97a35f2b38aa)
![image](https://github.com/user-attachments/assets/b9b6412d-c980-4ac4-9265-b604ef5ef552)
![image](https://github.com/user-attachments/assets/b0a43193-6af5-4aa5-95f3-8ae940737732)

### Схема БД

![image](https://github.com/user-attachments/assets/3ed45928-a64a-44de-9106-f5060851222f)

### Диаграмма классов

![package](https://github.com/user-attachments/assets/70ba6d55-92d8-4b20-8722-4ad818e995b1)

### Дизайн

![image](https://github.com/user-attachments/assets/4b682b34-e818-4e22-b830-cbee00e3bfa9)

## Документация

### API

https://github.com/LVerify/RIOPK/blob/develop/api-docs.json

## Тестирование

Пример модульного тестирования:

```java
     package by.hospital.diseasehistory;

     @ExtendWith(MockitoExtension.class)
     class PatientServiceTest {

     @Mock
     private PatientMapper patientMapper;

     @Mock
     private PatientRepository patientRepository;

     @InjectMocks
     private PatientService patientService;

     @BeforeEach
     void setUp() {
         MockitoAnnotations.openMocks(this);
     }

     @Test
     void testSavePatient_Success() {
         // Arrange
         Patient patient = new Patient();
         when(patientRepository.save(patient)).thenReturn(patient);

         // Act
         Patient result = patientService.savePatient(patient);

         // Assert
         assertEquals(patient, result);
         verify(patientRepository).save(patient);
     }

     @Test
     void testCreatePatient_Success() {
         // Arrange
         PatientCreateUpdateDTO dto = new PatientCreateUpdateDTO();
         Patient patient = new Patient();
         PatientReadDTO patientReadDTO = new PatientReadDTO();

         when(patientRepository.existsByDateOfBirthAndMobilePhone(dto.getDateOfBirth(), dto.getMobilePhone()))
                 .thenReturn(false);
         when(patientMapper.toEntity(dto)).thenReturn(patient);
         when(patientRepository.save(patient)).thenReturn(patient);
         when(patientMapper.toDto(patient)).thenReturn(patientReadDTO);

         // Act
         PatientReadDTO result = patientService.createPatient(dto);

         // Assert
         assertEquals(patientReadDTO, result);
         verify(patientRepository).existsByDateOfBirthAndMobilePhone(dto.getDateOfBirth(), dto.getMobilePhone());
         verify(patientMapper).toEntity(dto);
         verify(patientRepository).save(patient);
         verify(patientMapper).toDto(patient);
     }

     @Test
     void testCreatePatient_AlreadyExists() {
         // Arrange
         PatientCreateUpdateDTO dto = new PatientCreateUpdateDTO();

         when(patientRepository.existsByDateOfBirthAndMobilePhone(dto.getDateOfBirth(), dto.getMobilePhone()))
                 .thenReturn(true);

         // Act & Assert
         assertThrows(DataAlreadyExistsException.class, () -> patientService.createPatient(dto));
         verify(patientRepository).existsByDateOfBirthAndMobilePhone(dto.getDateOfBirth(), dto.getMobilePhone());
         verifyNoInteractions(patientMapper);
     }

     @Test
     void testUpdatePatient_Success() {
         // Arrange
         String id = "patientId";
         PatientCreateUpdateDTO dto = new PatientCreateUpdateDTO();
         Patient existingPatient = new Patient();
         PatientReadDTO patientReadDTO = new PatientReadDTO();

         when(patientRepository.findById(id)).thenReturn(Optional.of(existingPatient));
         when(patientRepository.save(existingPatient)).thenReturn(existingPatient);
         when(patientMapper.toDto(existingPatient)).thenReturn(patientReadDTO);

         // Act
         PatientReadDTO result = patientService.updatePatient(id, dto);

         // Assert
         assertEquals(patientReadDTO, result);
         verify(patientRepository).findById(id);
         verify(patientMapper).updateEntity(dto, existingPatient);
         verify(patientRepository).save(existingPatient);
         verify(patientMapper).toDto(existingPatient);
     }

     @Test
     void testUpdatePatient_NotFound() {
         // Arrange
         String id = "nonExistentId";
         PatientCreateUpdateDTO dto = new PatientCreateUpdateDTO();

         when(patientRepository.findById(id)).thenReturn(Optional.empty());

         // Act & Assert
         assertThrows(DataNotFoundException.class, () -> patientService.updatePatient(id, dto));
         verify(patientRepository).findById(id);
         verifyNoInteractions(patientMapper);
     }

     @Test
     void testGetPatient_Success() {
         // Arrange
         String id = "patientId";
         Patient patient = new Patient();
         PatientReadDTO patientReadDTO = new PatientReadDTO();

         when(patientRepository.findById(id)).thenReturn(Optional.of(patient));
         when(patientMapper.toDto(patient)).thenReturn(patientReadDTO);

         // Act
         PatientReadDTO result = patientService.getPatient(id);

         // Assert
         assertEquals(patientReadDTO, result);
         verify(patientRepository).findById(id);
         verify(patientMapper).toDto(patient);
     }

     @Test
     void testGetPatient_NotFound() {
         // Arrange
         String id = "nonExistentId";

         when(patientRepository.findById(id)).thenReturn(Optional.empty());

         // Act & Assert
         assertThrows(DataNotFoundException.class, () -> patientService.getPatient(id));
         verify(patientRepository).findById(id);
         verifyNoInteractions(patientMapper);
     }

     @Test
     void testGetAllPatients_Success() {
         // Arrange
         List<Patient> patients = List.of(new Patient(), new Patient());
         List<PatientReadDTO> patientReadDTOs = List.of(new PatientReadDTO(), new PatientReadDTO());

         when(patientRepository.findAll()).thenReturn(patients);
         when(patientMapper.toDto(any(Patient.class)))
                 .thenAnswer(invocation -> new PatientReadDTO()); // Пример, если маппер возвращает новую DTO

         // Act
         List<PatientReadDTO> result = patientService.getAllPatients();

         // Assert
         assertEquals(patientReadDTOs.size(), result.size());
         verify(patientRepository).findAll();
         verify(patientMapper, times(patients.size())).toDto(any(Patient.class));
     }

     @Test
     void testDeletePatient_Success() {
         // Arrange
         String id = "patientId";

         when(patientRepository.existsById(id)).thenReturn(true);

         // Act
         patientService.deletePatient(id);

         // Assert
         verify(patientRepository).existsById(id);
         verify(patientRepository).deleteById(id);
     }

     @Test
     void testDeletePatient_NotFound() {
         // Arrange
         String id = "nonExistentId";

         when(patientRepository.existsById(id)).thenReturn(false);

         // Act & Assert
         assertThrows(DataNotFoundException.class, () -> patientService.deletePatient(id));
         verify(patientRepository).existsById(id);
         verifyNoInteractions(patientMapper);
     }
    }
```

Описание Unit-тестов
testSavePatient_Success:
Arrange: Создаем объект пациента и настраиваем репозиторий для возврата этого объекта при вызове метода save.
Act: Вызываем метод savePatient сервиса с объектом пациента.
Assert: Проверяем, что результат равен переданному объекту пациента и что метод save репозитория был вызван.
testCreatePatient_Success:
Arrange: Создаем DTO для создания пациента, объект пациента и его DTO для чтения. Настраиваем методы репозитория и маппера, чтобы они возвращали ожидаемые значения.
Act: Вызываем метод createPatient сервиса с DTO.
Assert: Убеждаемся, что результат совпадает с DTO для чтения, и проверяем вызовы всех зависимостей.
testCreatePatient_AlreadyExists:
Arrange: Настраиваем репозиторий так, чтобы он сигнализировал о наличии пациента с указанными данными.
Act & Assert: Проверяем, что метод createPatient выбрасывает исключение DataAlreadyExistsException, а маппер не используется.
testUpdatePatient_Success:
Arrange: Создаем ID пациента, DTO для обновления, существующий объект пациента и его DTO для чтения. Настраиваем зависимости для возврата ожидаемых значений.
Act: Вызываем метод updatePatient сервиса.
Assert: Проверяем соответствие результата DTO для чтения и вызов всех ожидаемых методов.
testUpdatePatient_NotFound:
Arrange: Настраиваем репозиторий так, чтобы он не находил пациента с указанным ID.
Act & Assert: Проверяем, что метод updatePatient выбрасывает исключение DataNotFoundException, а маппер не используется.
testGetPatient_Success:
Arrange: Настраиваем репозиторий для возврата объекта пациента и маппер для преобразования его в DTO.
Act: Вызываем метод getPatient.
Assert: Убеждаемся, что результат совпадает с DTO, и проверяем вызовы методов репозитория и маппера.
testGetPatient_NotFound:
Arrange: Настраиваем репозиторий так, чтобы он не находил пациента.
Act & Assert: Проверяем, что метод getPatient выбрасывает исключение DataNotFoundException.
testGetAllPatients_Success:
Arrange: Настраиваем репозиторий для возврата списка пациентов и маппер для преобразования их в список DTO.
Act: Вызываем метод getAllPatients.
Assert: Проверяем, что размер возвращенного списка совпадает с размером списка DTO, и что маппер был вызван для каждого пациента.
testDeletePatient_Success:
Arrange: Настраиваем репозиторий для подтверждения наличия пациента.
Act: Вызываем метод deletePatient.
Assert: Проверяем вызовы методов проверки существования и удаления.
testDeletePatient_NotFound:
Arrange: Настраиваем репозиторий так, чтобы он сигнализировал об отсутствии пациента.
Act & Assert: Проверяем, что метод deletePatient выбрасывает исключение DataNotFoundException.
