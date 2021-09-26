# Alkemy Challenge en Spring

## Introducci�n
**API REST** utilizando Spring Boot, Spring Security (usando *JWT* para la autenticaci�n) y Spring Data. La resoluci�n presentada no tiene un *frontend* desarrollado.

## Documentaci�n
Para la documentaci�n de los *endpoints* se utiliz� Postman. Sumado a esta, m�s abajo se explican  algunas de las decisiones o problem�ticas de dise�o que se presentaron a la hora de realizar el challenge.
La documentaci�n puede encontrarse [aqu�](https://documenter.getpostman.com/view/17666056/UUxxgoRg).

## Breve informe de la realizaci�n del challenge
Durante el desarrollo del challenge, se intent� generar c�digo limpio. Principalmente, se intent� aplicar el concepto de *clean code* que enuncia que el c�digo �se explique por s� solo�. El objetivo personal del challenge fue iniciar en el aprendizaje de **Spring**.

A continuaci�n, se detallan en �tems las principales decisiones de dise�o tomadas:
* No se utiliz� paginaci�n a la hora de retornar los resultados. Esta decisi�n se tom� teniendo en cuenta que no es un escenario de producci�n real. Por lo cual, se prefiri� hacer foco en resolver el problema planteado en la consigna.
* Siguiendo la misma pauta del inciso anterior, al momento de subir una imagen, si bien se valida que se trate efectivamente de una imagen, el tama�o de �sta no es verificado. Es decir, su limitaci�n es la que tiene por defecto un *String* con *Spring Validations*.
* Como se mencion� en el inciso anterior, una imagen es representada usando un *String*. Esto se debe a que la imagen se codifica en **Base64**. Si bien se analiz� la posibilidad de colocar un endpoint espec�fico para la imagen, en cual esta se reciba utilizando un *content-type* de *multipart*, se opt� por esta soluci�n dado que es cercana a la consigna (adem�s ser m�s cercana a la idea de **API REST**).
* Al momento de tener que filtrar cierta informaci�n de una entidad en algunas ocasiones y en otras no, se podr�an haber creado m�s de un *DTO* para esa entidad. Sin embargo, se opt� por no mostrar los atributos nulos del *DTO* y filtrar qu� mostrar del *DTO* dejando como nulo aquello que no se desea mostrar. De todos modos, se cre� un segundo *DTO* por cada entidad que admit�a b�squeda y filtrado. Esto con el fin de tener un c�digo m�s desacoplado.
* Al momento de establecer la relaci�n *muchos a muchos* entre las **pel�culas** y los **personajes** en *Spring Data*, se estableci� como due�o de la relaci�n a los **personajes**. Simplemente fue una convenci�n. Sin embargo, durante el desarrollo, se lleg� a la conclusi�n de que ser�a m�s l�gico que **pel�cula** fuera el due�o de la relaci�n. Finalmente, se prefiri� mantener la convenci�n inicial.
* Se presentaron dificultades de decisi�n al momento de armar los *endpoints* para los **personajes** y las **pel�culas**. Especialmente sobre c�mo realizar la asociaci�n entre los mismos. Se pens� la posibilidad de que al momento de crear un **personaje** (o una pel�cula) se deba especificar una lista de ids de **pel�culas** (o personajes) a las cuales est� asociada el **personaje** (o pel�cula). Sin embargo, finalmente se opt� por impedir establecer la asociaci�n entre **personajes** y **pel�culas** al momento de su creaci�n y designar un *endpoint* espec�fico para realizarla.
* Siguiendo con el inciso anterior, en el caso de la relaci�n *muchos a uno* entre **pel�culas** y **g�neros**, se prefiri� que al momento de la creaci�n de una **pel�cula** se deba especificar el id del **g�nero** al cual pertenece la misma. Principalmente, buscando eliminar la posibilidad de que una **pel�cula** no pertenezca a ning�n **g�nero**.
* Se podr�a haber realizado un *controlador* y/o *servicio* gen�rico, con el fin de reutilizar c�digo. Sin bien se consider�, por cuestiones de tiempo se prefiri� dejar como una caracter�stica mejorable.
* Del mismo modo, los *mappers* creados son mejorables. Se podr�a haber creado un *mapper* gen�rico o utilizar alguna librer�a para realizar los mapeos entre un *DTO* y una entidad.

Por �ltimo, cabe resaltar que no se realiz� el ejercicio opcional de testing.

## Enunciado del challenge realizado
El enunciado del proyecto puede encontrarse en el archivo [enunciado.pdf](./enunciado.pdf)