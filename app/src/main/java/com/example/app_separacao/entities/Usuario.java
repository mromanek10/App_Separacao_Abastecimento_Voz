package com.example.app_separacao.entities;

import java.io.Serializable;

public class Usuario implements Serializable {

    private int usuario_ID;
    private String usuario_Login;
    private String usuario_Senha;
    private String usuario_DtUltimoAcesso;
    private String usuario_DtExpiracao;
    private int usuario_IsAtivo;

    public Usuario()
    {}

    public Usuario(int usuario_ID, String usuario_Login,String usuario_Senha, String usuario_DtExpiracao, int usuario_IsAtivo){
        this.usuario_ID = usuario_ID;
        this.usuario_Login = usuario_Login;
        this.usuario_Senha=usuario_Senha;
      //  this.usuario_DtUltimoAcesso = usuario_DtUltimoAcesso;
        this.usuario_DtExpiracao = usuario_DtExpiracao;
        this.usuario_IsAtivo = usuario_IsAtivo;
    }

    public int getUsuarioId(){ return this.usuario_ID; }
    public String getusuarioLogin(){ return this.usuario_Login; }
    public String getUsuarioSenha(){ return this.usuario_Senha; }
    public String getUsuarioUltimoAcesso(String string){ return this.usuario_DtUltimoAcesso; }
    public String getUsuarioDTExpiracao(){ return this.usuario_DtExpiracao; }
    public int getUsuarioIsAtivo(){ return this.usuario_IsAtivo; }
    public void setUsuarioId(int usuario_ID)
    {
        this.usuario_ID = usuario_ID;
    }
    public void setUsuarioLogin(String usuario_Login)
    {
        this.usuario_Login = usuario_Login;
    }
    public void setUsuarioSenha(String usuario_Senha)
    {
        this.usuario_Senha = usuario_Senha;
    }
    public void setUsuarioDTExpiracao(String usuario_DtExpiracao)
    {
        this.usuario_DtExpiracao = usuario_DtExpiracao;
    }
    public void setUsuarioIsAtivo(int usuario_IsAtivo)
    {
        this.usuario_IsAtivo = usuario_IsAtivo;
    }

    @Override
    public boolean equals(Object o){
        return this.usuario_ID == ((Usuario)o).usuario_ID;
    }

    @Override
    public int hashCode(){
        return this.usuario_ID;
    }
}