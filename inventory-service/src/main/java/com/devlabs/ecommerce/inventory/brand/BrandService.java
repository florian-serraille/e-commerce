package com.devlabs.ecommerce.inventory.brand;

import com.devlabs.ecommerce.inventory.exception.ResourceInUseException;
import com.devlabs.ecommerce.inventory.exception.ResourceNotFoundException;
import com.devlabs.ecommerce.inventory.validation.OnCreate;
import com.devlabs.ecommerce.inventory.validation.OnUpdate;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Service
@Validated
@AllArgsConstructor
public class BrandService {
	
	private final BrandRepository brandRepository;
	private final BrandMapper brandMapper;
	
	List<ApiBrand> findAll() {
		return brandMapper.toDTO(brandRepository.findAll());
	}
	
	public ApiBrand findById(@NotNull final Long brandId) {
		return brandMapper.toDTO(findModelById(brandId));
	}
	
	public Brand findModelById(@NotNull final Long brandId) {
		return brandRepository.findById(brandId)
		                      .orElseThrow(() -> new ResourceNotFoundException(
				                      "Brand not found for id: " + brandId));
	}
	
	@Validated(OnCreate.class)
	ApiBrand save(@Valid final ApiBrand apiBrand) {
		
		final Brand brand = brandMapper.toModel(apiBrand);
		return brandMapper.toDTO(brandRepository.save(brand));
	}
	
	@Validated(OnUpdate.class)
	ApiBrand update(@NotNull final Long brandId, @Valid final ApiBrand apiBrand) {
		
		final Brand brand = findModelById(brandId);
		final Brand updatedBrand = brandMapper.updateModel(brand, apiBrand);
		return brandMapper.toDTO(brandRepository.save(updatedBrand));
	}
	
	void delete(@NotNull final Long brandId) {
		
		final Brand brand = findModelById(brandId);
		
		try {
			brandRepository.delete(brand);
			
		} catch (DataIntegrityViolationException exception) {
			throw new ResourceInUseException("Error to delete brand for id " + brandId + ". " +
			                                 "Still some product(s) using this brand");
		}
	}
}

