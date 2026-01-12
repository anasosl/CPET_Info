import { Directive, ElementRef, HostListener, Renderer2 } from '@angular/core';

@Directive({
  selector: '[appHighlight]',
  standalone: true
})
export class HighlightDirective {
  constructor(private el: ElementRef, private renderer: Renderer2) {}

  // Detecta o toque/clique para mudar a cor de fundo 
  @HostListener('click') onClick() {
    this.renderer.setStyle(this.el.nativeElement, 'backgroundColor', '#f0f0f0');
  }
}