package com.example.donapp;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u0013B\'\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u0012\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\b0\u0007\u00a2\u0006\u0002\u0010\tJ\b\u0010\n\u001a\u00020\u000bH\u0016J\u0018\u0010\f\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\u00022\u0006\u0010\u000e\u001a\u00020\u000bH\u0016J\u0018\u0010\u000f\u001a\u00020\u00022\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u000bH\u0016R\u001a\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\b0\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0014"}, d2 = {"Lcom/example/donapp/ProductoPublicoAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/example/donapp/ProductoPublicoAdapter$ProductoViewHolder;", "productos", "", "Lcom/example/donapp/Producto;", "onInteresClick", "Lkotlin/Function1;", "", "(Ljava/util/List;Lkotlin/jvm/functions/Function1;)V", "getItemCount", "", "onBindViewHolder", "holder", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "ProductoViewHolder", "app_debug"})
public final class ProductoPublicoAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<com.example.donapp.ProductoPublicoAdapter.ProductoViewHolder> {
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.example.donapp.Producto> productos = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.jvm.functions.Function1<com.example.donapp.Producto, kotlin.Unit> onInteresClick = null;
    
    public ProductoPublicoAdapter(@org.jetbrains.annotations.NotNull()
    java.util.List<com.example.donapp.Producto> productos, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.example.donapp.Producto, kotlin.Unit> onInteresClick) {
        super();
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public com.example.donapp.ProductoPublicoAdapter.ProductoViewHolder onCreateViewHolder(@org.jetbrains.annotations.NotNull()
    android.view.ViewGroup parent, int viewType) {
        return null;
    }
    
    @java.lang.Override()
    public void onBindViewHolder(@org.jetbrains.annotations.NotNull()
    com.example.donapp.ProductoPublicoAdapter.ProductoViewHolder holder, int position) {
    }
    
    @java.lang.Override()
    public int getItemCount() {
        return 0;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\t\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\r\u001a\u00020\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0011\u001a\u00020\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0010R\u0011\u0010\u0013\u001a\u00020\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0010\u00a8\u0006\u0015"}, d2 = {"Lcom/example/donapp/ProductoPublicoAdapter$ProductoViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "view", "Landroid/view/View;", "(Landroid/view/View;)V", "btnMeInteresa", "Landroid/widget/Button;", "getBtnMeInteresa", "()Landroid/widget/Button;", "imgProducto", "Landroid/widget/ImageView;", "getImgProducto", "()Landroid/widget/ImageView;", "txtDescripcion", "Landroid/widget/TextView;", "getTxtDescripcion", "()Landroid/widget/TextView;", "txtDonador", "getTxtDonador", "txtNombre", "getTxtNombre", "app_debug"})
    public static final class ProductoViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        @org.jetbrains.annotations.NotNull()
        private final android.widget.ImageView imgProducto = null;
        @org.jetbrains.annotations.NotNull()
        private final android.widget.TextView txtNombre = null;
        @org.jetbrains.annotations.NotNull()
        private final android.widget.TextView txtDescripcion = null;
        @org.jetbrains.annotations.NotNull()
        private final android.widget.TextView txtDonador = null;
        @org.jetbrains.annotations.NotNull()
        private final android.widget.Button btnMeInteresa = null;
        
        public ProductoViewHolder(@org.jetbrains.annotations.NotNull()
        android.view.View view) {
            super(null);
        }
        
        @org.jetbrains.annotations.NotNull()
        public final android.widget.ImageView getImgProducto() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final android.widget.TextView getTxtNombre() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final android.widget.TextView getTxtDescripcion() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final android.widget.TextView getTxtDonador() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final android.widget.Button getBtnMeInteresa() {
            return null;
        }
    }
}