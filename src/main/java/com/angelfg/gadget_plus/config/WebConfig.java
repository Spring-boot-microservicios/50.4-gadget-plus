package com.angelfg.gadget_plus.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

/**
 * El Error de sebe a la forma en que se están serializando las instancias de PageImpl en tu aplicación Spring.
 * Al serializar directamente estas instancias, no se garantiza la estabilidad de la estructura JSON resultante,
 * lo que puede causar problemas en la forma en que se consumen los datos en el frontend.
 *
 * Puedes configurar tu aplicación para que use PagedModel en lugar de PageImpl directamente.
 * Para hacer esto, añade la anotación en tu clase de configuración
 *
 * Solucion para error de log en consola al realizar el paginado
 *
 */
@Configuration
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
public class WebConfig {

}