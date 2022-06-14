package org.filmland.catalog.web.controllers;

import lombok.RequiredArgsConstructor;
import org.filmland.catalog.service.FilmCategoryService;
import org.filmland.catalog.utils.CurrencyConverterUtils;
import org.filmland.catalog.web.dto.*;
import org.filmland.catalog.web.errors.ErrorCodes;
import org.filmland.catalog.web.errors.FilmLandFeatureException;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "categories")
public class FilmCategoryController {

    private final FilmCategoryService filmCategoryService;
    private final ModelMapper modelMapper;

    private static final Type categoryListTypeToken = new TypeToken<List<CategoryResponseDTO>>() {
    }.getType();

    @GetMapping
    public CategoriesListResponseDTO getAll(@RequestParam(name = "page", defaultValue = "0") int page,
                                            @RequestParam(name = "size", defaultValue = "10") int size, Principal principal) {

        List<SubscribedCategoryResponseDTO> subscribedCategoryResponseDTOList =
                filmCategoryService.getSubscribedCategories(principal.getName())
                        .stream()
                        .map(subscription -> {
                            SubscribedCategoryResponseDTO subscribedCategoryResponseDTO = modelMapper.map(subscription, SubscribedCategoryResponseDTO.class);
                            subscribedCategoryResponseDTO.setPrice(CurrencyConverterUtils.convertPrice(subscription.getFilmCategory().getCurrency(), subscription.getFilmCategory().getPrice()));
                            return subscribedCategoryResponseDTO;
                        })
                        .collect(Collectors.toList());

        List<CategoryResponseDTO> availableCategoryResponseDTOList =
                filmCategoryService.getAllFilmCategories(page, size)
                        .stream()
                        .map(category -> {
                            CategoryResponseDTO categoryResponseDTO= modelMapper.map(category,CategoryResponseDTO.class);
                            categoryResponseDTO.setPrice(CurrencyConverterUtils.convertPrice(category.getCurrency(),category.getPrice()));
                            return categoryResponseDTO;
                        })
                        .collect(Collectors.toList());

        return CategoriesListResponseDTO
                .builder()
                .availableCategories(availableCategoryResponseDTOList)
                .subscribedCategories(subscribedCategoryResponseDTOList)
                .build();
    }

    @PostMapping
    public CategorySubscribeResponseDTO subscribeCategory(Principal principal, @RequestBody CategorySubscriptionRequestDTO categorySubscriptionRequestDTO) {
        if (!principal.getName().equalsIgnoreCase(categorySubscriptionRequestDTO.getEmail())) {
            throw new FilmLandFeatureException(ErrorCodes.OPERATION_NOT_PERMITTED);
        }
        filmCategoryService.addSubscription(principal.getName(), categorySubscriptionRequestDTO.getCustomer(), categorySubscriptionRequestDTO.getAvailableCategories());

        return new CategorySubscribeResponseDTO("Subscription added", "Successfully added subscription");
    }
}
