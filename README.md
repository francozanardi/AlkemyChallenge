# Alkemy Challenge en Spring

### Introducción
**API REST** utilizando Spring Boot, Spring Security (usando *JWT* para la autenticación) y Spring Data. La resolución presentada no tiene un *frontend* desarrollado.

### Documentación
Para la documentación de los *endpoints* se utilizó Postman. Sumado a esta, más abajo se explican  algunas de las decisiones o problemáticas de diseño que se presentaron a la hora de realizar el challenge.
La documentación puede encontrarse [aquí](https://documenter.getpostman.com/view/17666056/UUxxgoRg).

### Breve informe de la realización del challenge
Durante el desarrollo del challenge, se intentó generar código limpio. Principalmente, se intentó aplicar el concepto de *clean code* que enuncia que el código “se explique por sí solo”. El objetivo personal del challenge fue iniciar en el aprendizaje de **Spring**.

A continuación, se detallan en ítems las principales decisiones de diseño tomadas:
* No se utilizó paginación a la hora de retornar los resultados. Esta decisión se tomó teniendo en cuenta que no es un escenario de producción real. Por lo cual, se prefirió hacer foco en resolver el problema planteado en la consigna.
* Siguiendo la misma pauta del inciso anterior, al momento de subir una imagen, si bien se valida que se trate efectivamente de una imagen, el tamaño de ésta no es verificado. Es decir, su limitación es la que tiene por defecto un *String* con *Spring Validations*.
* Como se mencionó en el inciso anterior, una imagen es representada usando un *String*. Esto se debe a que la imagen se codifica en **Base64**. Si bien se analizó la posibilidad de colocar un endpoint específico para la imagen, en cual esta se reciba utilizando un *content-type* de *multipart*, se optó por esta solución dado que es cercana a la consigna (además ser más cercana a la idea de **API REST**).
* Al momento de tener que filtrar cierta información de una entidad en algunas ocasiones y en otras no, se podrían haber creado más de un *DTO* para esa entidad. Sin embargo, se optó por no mostrar los atributos nulos del *DTO* y filtrar qué mostrar del *DTO* dejando como nulo aquello que no se desea mostrar. De todos modos, se creó un segundo *DTO* por cada entidad que admitía búsqueda y filtrado. Esto con el fin de tener un código más desacoplado.
* Al momento de establecer la relación *muchos a muchos* entre las **películas** y los **personajes** en *Spring Data*, se estableció como dueño de la relación a los **personajes**. Simplemente fue una convención. Sin embargo, durante el desarrollo, se llegó a la conclusión de que sería más lógico que **película** fuera el dueño de la relación. Finalmente, se prefirió mantener la convención inicial.
* Se presentaron dificultades de decisión al momento de armar los *endpoints* para los **personajes** y las **películas**. Especialmente sobre cómo realizar la asociación entre los mismos. Se pensó la posibilidad de que al momento de crear un **personaje** (o una película) se deba especificar una lista de ids de **películas** (o personajes) a las cuales está asociada el **personaje** (o película). Sin embargo, finalmente se optó por impedir establecer la asociación entre **personajes** y **películas** al momento de su creación y designar un *endpoint* específico para realizarla.
* Siguiendo con el inciso anterior, en el caso de la relación *muchos a uno* entre **películas** y **géneros**, se prefirió que al momento de la creación de una **película** se deba especificar el id del **género** al cual pertenece la misma. Principalmente, buscando eliminar la posibilidad de que una **película** no pertenezca a ningún **género**.
* Se podría haber realizado un *controlador* y/o *servicio* genérico, con el fin de reutilizar código. Sin bien se consideró, por cuestiones de tiempo se prefirió dejar como una característica mejorable.
* Del mismo modo, los *mappers* creados son mejorables. Se podría haber creado un *mapper* genérico o utilizar alguna librería para realizar los mapeos entre un *DTO* y una entidad.

Por último, cabe resaltar que no se realizó el ejercicio opcional de testing.

### Enunciado del challenge realizado
El enunciado del proyecto puede encontrarse en el archivo [enunciado.pdf](./enunciado.pdf)
