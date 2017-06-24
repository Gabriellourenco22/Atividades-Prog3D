package br.pucpr.mage.phong;

import org.joml.Vector3f;

import br.pucpr.mage.Shader;

public class DirectionalLight {
    private Vector3f diffuse;
    private Vector3f specular;
    private Vector3f direction;
    private Vector3f ambient;


    public DirectionalLight(Vector3f directionV, Vector3f ambientV, Vector3f diffuseV, Vector3f specularV) {
        super();

        this.ambient = ambientV;
        this.specular = specularV;
        this.direction = directionV;
        this.diffuse = diffuseV;
    }

    public Vector3f getDirection() {
        return direction;
    }

    public Vector3f getAmbient() {
        return ambient;
    }

    public Vector3f getDiffuse() {
        return diffuse;
    }

    public Vector3f getSpecular() {
        return specular;
    }

    public void apply(Shader shader) {
        shader.setUniform("uLightDir", direction);
        shader.setUniform("uAmbientLight", ambient);
        shader.setUniform("uDiffuseLight", diffuse);
        shader.setUniform("uSpecularLight", specular);
    }
}
