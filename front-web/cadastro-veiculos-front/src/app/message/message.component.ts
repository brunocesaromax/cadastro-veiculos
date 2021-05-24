import { Component, Input } from '@angular/core';
import { FormControl } from "@angular/forms";

@Component({
  selector: 'app-message',
  template: `
    <div *ngIf="hasError()" class="ui-message ui-message-error">
      {{text}}
    </div>
  `,
  styles: [`
    .ui-message-error {
      margin: 4px 0 0;
    }
  `]
})
export class MessageComponent {
  @Input() error: string = '';
  @Input() control: FormControl = new FormControl();
  @Input() text: string = '';

  hasError() {
    return this.control.hasError(this.error) && this.control.dirty;
  }
}
