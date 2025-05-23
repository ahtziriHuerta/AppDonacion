package com.example.donapp;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0012\u001a\u00020\u0013H\u0002J\b\u0010\u0014\u001a\u00020\u0013H\u0002J\u0010\u0010\u0015\u001a\u00020\u00132\u0006\u0010\u0016\u001a\u00020\u000bH\u0002J\u0010\u0010\u0017\u001a\u00020\u00132\u0006\u0010\u0016\u001a\u00020\u000bH\u0002J\u0010\u0010\u0018\u001a\u00020\u00132\u0006\u0010\u0016\u001a\u00020\u000bH\u0002J\u0012\u0010\u0019\u001a\u00020\u00132\b\u0010\u001a\u001a\u0004\u0018\u00010\u001bH\u0014J\b\u0010\u001c\u001a\u00020\u0013H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0010\u001a\u0004\u0018\u00010\u0011X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001d"}, d2 = {"Lcom/example/donapp/ListaProductosActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "adapter", "Lcom/example/donapp/ProductoAdapter;", "btnAgregarProducto", "Landroid/widget/Button;", "db", "Lcom/google/firebase/firestore/FirebaseFirestore;", "productos", "", "Lcom/example/donapp/Producto;", "recyclerView", "Landroidx/recyclerview/widget/RecyclerView;", "textoVacio", "Landroid/widget/TextView;", "usuarioIdActual", "", "asignarUsuarioActualATodosLosProductos", "", "cargarProductos", "eliminarDocumentoFirestore", "producto", "eliminarLocalmente", "eliminarProducto", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "reemplazarImagenesRotasConDefecto", "app_debug"})
public final class ListaProductosActivity extends androidx.appcompat.app.AppCompatActivity {
    private androidx.recyclerview.widget.RecyclerView recyclerView;
    private android.widget.TextView textoVacio;
    private android.widget.Button btnAgregarProducto;
    @org.jetbrains.annotations.NotNull()
    private final com.google.firebase.firestore.FirebaseFirestore db = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.example.donapp.Producto> productos = null;
    private com.example.donapp.ProductoAdapter adapter;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String usuarioIdActual = null;
    
    public ListaProductosActivity() {
        super();
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void cargarProductos() {
    }
    
    private final void eliminarProducto(com.example.donapp.Producto producto) {
    }
    
    private final void eliminarDocumentoFirestore(com.example.donapp.Producto producto) {
    }
    
    private final void eliminarLocalmente(com.example.donapp.Producto producto) {
    }
    
    private final void asignarUsuarioActualATodosLosProductos() {
    }
    
    private final void reemplazarImagenesRotasConDefecto() {
    }
}