package mnshat.dev.myproject.commonFeatures.getLibraryContent.domain.useCase

import mnshat.dev.myproject.commonFeatures.getLibraryContent.data.LibraryContentRepo

class GetLibraryContentUseCase(private val libraryRepository: LibraryContentRepo) {


    suspend operator fun invoke() =
      libraryRepository.getLibraryContent()
}