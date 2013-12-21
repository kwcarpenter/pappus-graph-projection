package com.jschneider.pappus;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

public class GraphObjectMapper {
	ObjectMapper oMapper = new ObjectMapper();
	MapToVertexMapper vMapper; 
	
	public GraphObjectMapper(Graph g) {
		oMapper.setSerializationInclusion(Include.NON_NULL);
		vMapper = new MapToVertexMapper(g);
	}
	
	@SuppressWarnings("unchecked")
	public Vertex toGraph(Object o) {
		Map<Object, Object> m = oMapper.convertValue(o, HashMap.class);
		return vMapper.toGraph(m, o.getClass());
	}
	
	public <E> E fromGraph(Vertex v, Class<E> clazz) {
		if(v == null)
			return null;
		Map<Object, Object> m = vMapper.fromGraph(v);
		return oMapper.convertValue(m, clazz);
	}
	
	public ObjectMapper getObjectMapper() {
		return oMapper;
	}

	public GraphObjectMapper typeKey(String typeKey) {
		vMapper.typeKey = typeKey;
		return this;
	}

	public GraphObjectMapper indexKey(String indexKey) {
		vMapper.indexKey = indexKey;
		return this;
	}
	
	public GraphObjectMapper hashKey(String hashKey) {
		vMapper.hashKey = hashKey;
		return this;
	}
}