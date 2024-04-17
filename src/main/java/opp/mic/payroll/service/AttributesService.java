package opp.mic.payroll.service;

import opp.mic.payroll.exceptions.CategoryNotFoundException;
import opp.mic.payroll.model.Attributes;
import opp.mic.payroll.model.ProductAttributeRequest;
import opp.mic.payroll.model.ProductSKU;
import opp.mic.payroll.repository.AttributesRepository;
import opp.mic.payroll.repository.ProductSKURepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AttributesService {

    private AttributesRepository attributesRepository;
    private ProductSKURepository productSKURepository;

    public AttributesService(AttributesRepository attributesRepository,
                             ProductSKURepository productSKURepository) {
        this.attributesRepository = attributesRepository;
        this.productSKURepository = productSKURepository;
    }

    public Page<Attributes> getAllAttributes(int page){
        PageRequest pr = PageRequest.of(page,5);
        Page<Attributes>  productAttributes = attributesRepository.findAll(pr);
        for(Attributes pa:productAttributes){
        pa.setProductSKU(productSKURepository.findAll().stream()
                .filter(f->f.getAttributes().getId().equals(pa.getId())).collect(Collectors.toList()));
         }
        return productAttributes;
    }

    @Transactional
    public Attributes save(ProductAttributeRequest productAttributeRequest){

        //check whether attribute already exist
        Optional<Attributes> savedAttribute = attributesRepository.findByName(productAttributeRequest.name());
        System.out.println(savedAttribute);
        if(savedAttribute.isPresent()){
            //save only new value
            ProductSKU productSKU = new ProductSKU(savedAttribute.get(),productAttributeRequest.value());
            productSKURepository.save(productSKU);
            return savedAttribute.get();
        }
        //attribute does not exist ,save new attribute and value
        Attributes attributes = new Attributes(productAttributeRequest.name());
        Attributes attribute= attributesRepository.save(attributes);
        ProductSKU productSKU = new ProductSKU(attribute,productAttributeRequest.value());
        productSKURepository.save(productSKU);
        return attribute;
    }

    public Attributes getById(Long id){
        Attributes attributes= attributesRepository.findById(id)
                .orElseThrow(()-> new CategoryNotFoundException("Category "+id +" does not exist"));
      List<ProductSKU> sku= productSKURepository.findAll().stream()
              .filter(a->a.getAttributes().getId().equals(attributes.getId())).toList();
      attributes.setProductSKU(sku);
      return attributes;
    }

    public boolean deleteAttribute(Long id){
        Attributes attributes= attributesRepository.findById(id)
                .orElseThrow(()-> new CategoryNotFoundException("Category "+id +" does not exist"));
        List<ProductSKU> sku= productSKURepository.findAll().stream()
                .filter(a->a.getAttributes().getId().equals(attributes.getId())).toList();
        if(sku.size()>0){
            return false;
        }else{
            attributesRepository.deleteById(id);
            return true;
        }

    }

    public ProductSKU deleteSKU(Long id){
        productSKURepository.deleteById(id);
        return productSKURepository.findById(id).orElse(null);
    }


}
