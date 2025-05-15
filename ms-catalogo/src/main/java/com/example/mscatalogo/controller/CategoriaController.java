package com.example.mscatalogo.controller;

import com.example.mscatalogo.entity.Categoria;
import com.example.mscatalogo.service.CategoriaService;
import com.example.mscatalogo.util.PdfUtils;
import com.example.mscatalogo.util.UserExcelExporter;
import com.itextpdf.text.DocumentException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/categoria")
public class CategoriaController {
    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public ResponseEntity<List<Categoria>> listar() {
        return ResponseEntity.ok(categoriaService.listar());
    }

    @PostMapping
    public ResponseEntity<Categoria> guardar(@RequestBody Categoria categoria) {
        return ResponseEntity.ok(categoriaService.guardar(categoria));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Categoria> buscarPorId(@PathVariable(required = true) Integer id) {
        return ResponseEntity.ok(categoriaService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Categoria> editar(@PathVariable(required = true) Integer id,
                                            @RequestBody Categoria categoria) {
        categoria.setId(id);
        return ResponseEntity.ok(categoriaService.editar(categoria));

    }

    @DeleteMapping("/{id}")
    public String eliminar(@PathVariable(required = true) Integer id) {
        categoriaService.eliminar(id);
        return "Eliminacion correcta";
    }
    @GetMapping("/pdf")
    public ResponseEntity<byte[]> exportPdf() throws IOException, DocumentException {
       // List<Map<String, Object>> queryResults = myService.executeQuery(request);
        ByteArrayOutputStream pdfStream = PdfUtils.generatePdfStream(categoriaService.listar()
        );
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=query_results.pdf");
        headers.setContentLength(pdfStream.size());
        return new ResponseEntity<>(pdfStream.toByteArray(), headers, HttpStatus.OK);
    }
    @GetMapping("/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        List<Categoria> categorias = categoriaService.listar();

        UserExcelExporter excelExporter = new UserExcelExporter(categorias);

        excelExporter.export(response);
    }
}
